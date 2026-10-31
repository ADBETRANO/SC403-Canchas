package com.golazo.service;

import com.golazo.domain.Cancha;
import com.golazo.domain.Espacio;
import com.golazo.domain.Horario;
import com.golazo.domain.Pago;
import com.golazo.domain.Reserva;
import com.golazo.domain.Tarifa;
import com.golazo.domain.Usuario;
import com.golazo.repository.CanchaRepository;
import com.golazo.repository.HorarioRepository;
import com.golazo.repository.PagoRepository;
import com.golazo.repository.ReservaRepository;
import com.golazo.repository.TarifaRepository;
import com.golazo.repository.UsuarioRepository;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
 * Servicio de reservas y pagos (HU-11 a HU-15).
 * Cuando una regla de negocio no se cumple se lanza una excepción cuyo
 * mensaje es la llave del archivo messages.properties que se le muestra al usuario.
 */
@Service
@RequiredArgsConstructor
public class ReservaService {

    //Desde las 18:00 se cobra la tarifa nocturna
    private static final LocalTime INICIO_NOCHE = LocalTime.of(18, 0);

    private final ReservaRepository reservaRepository;
    private final PagoRepository pagoRepository;
    private final HorarioRepository horarioRepository;
    private final TarifaRepository tarifaRepository;
    private final CanchaRepository canchaRepository;
    private final UsuarioRepository usuarioRepository;

    //"Lunes a viernes" o "Fin de semana" según la fecha
    public String getTipoDia(LocalDate fecha) {
        DayOfWeek dia = fecha.getDayOfWeek();
        return (dia == DayOfWeek.SATURDAY || dia == DayOfWeek.SUNDAY) ? "Fin de semana" : "Lunes a viernes";
    }

    //"Diurna" antes de las 18:00 y "Nocturna" desde las 18:00
    public String getTipoTarifa(LocalTime hora) {
        return hora.isBefore(INICIO_NOCHE) ? "Diurna" : "Nocturna";
    }

    //Precio por hora vigente de una cancha a cierta hora (regla 3)
    @Transactional(readOnly = true)
    public Optional<BigDecimal> getPrecio(Integer idCancha, LocalTime hora) {
        Optional<Tarifa> tarifa = tarifaRepository
                .findFirstByCanchaIdCanchaAndTipoTarifaAndVigenteTrue(idCancha, getTipoTarifa(hora));
        if (tarifa.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(tarifa.get().getPrecioHora());
    }

    //Horario de atención de una cancha según el día de la fecha
    @Transactional(readOnly = true)
    public Optional<Horario> getHorario(Integer idCancha, LocalDate fecha) {
        return horarioRepository.findFirstByCanchaIdCanchaAndDia(idCancha, getTipoDia(fecha));
    }

    //HU-11: agenda de la cancha en una fecha, en espacios de una hora
    @Transactional(readOnly = true)
    public List<Espacio> getDisponibilidad(Integer idCancha, LocalDate fecha) {
        List<Espacio> espacios = new ArrayList<>();
        Optional<Horario> horarioOpt = horarioRepository.findFirstByCanchaIdCanchaAndDia(idCancha, getTipoDia(fecha));
        if (horarioOpt.isEmpty()) {
            return espacios; //La cancha no atiende ese día
        }
        Horario horario = horarioOpt.get();
        //Horas que ya están reservadas ese día (sin contar las canceladas)
        List<LocalTime> ocupadas = new ArrayList<>();
        for (Reserva r : reservaRepository.findByCanchaIdCanchaAndFechaAndEstadoNot(idCancha, fecha, "Cancelada")) {
            ocupadas.add(r.getHoraInicio());
        }
        LocalDateTime ahora = LocalDateTime.now();
        LocalTime hora = horario.getHoraApertura();
        //Se generan espacios de una hora mientras quepan antes del cierre
        while (hora.plusHours(1).isAfter(hora) && !hora.plusHours(1).isAfter(horario.getHoraCierre())) {
            Optional<BigDecimal> precio = getPrecio(idCancha, hora);
            boolean libre = !ocupadas.contains(hora)
                    && fecha.atTime(hora).isAfter(ahora)
                    && precio.isPresent();
            Espacio espacio = new Espacio();
            espacio.setInicio(hora);
            espacio.setFin(hora.plusHours(1));
            espacio.setDisponible(libre);
            espacio.setTipoTarifa(getTipoTarifa(hora));
            espacio.setPrecio(precio.isPresent() ? precio.get() : null);
            espacios.add(espacio);
            hora = hora.plusHours(1);
        }
        return espacios;
    }

    //HU-12: registra una reserva "Pendiente" de una hora
    @Transactional
    public Reserva crearReserva(Integer idCancha, Integer idUsuario, LocalDate fecha, LocalTime horaInicio) {
        Optional<Cancha> canchaOpt = canchaRepository.findById(idCancha);
        if (canchaOpt.isEmpty()) {
            throw new IllegalArgumentException("reserva.error03");
        }
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(idUsuario);
        if (usuarioOpt.isEmpty()) {
            throw new IllegalArgumentException("usuario.error01");
        }
        Cancha cancha = canchaOpt.get();
        Usuario usuario = usuarioOpt.get();
        LocalTime horaFin = horaInicio.plusHours(1);

        //Regla 2: solo canchas activas, dentro del horario y en el futuro
        Optional<Horario> horarioOpt = horarioRepository.findFirstByCanchaIdCanchaAndDia(idCancha, getTipoDia(fecha));
        if (horarioOpt.isEmpty()) {
            throw new IllegalStateException("reserva.error03");
        }
        Horario horario = horarioOpt.get();
        if (!cancha.isActivo()
                || horaInicio.isBefore(horario.getHoraApertura())
                || horaFin.isAfter(horario.getHoraCierre())
                || !fecha.atTime(horaInicio).isAfter(LocalDateTime.now())) {
            throw new IllegalStateException("reserva.error03");
        }
        //Regla 1: no puede haber dos reservas en la misma fecha y hora
        if (reservaRepository.existsByCanchaIdCanchaAndFechaAndHoraInicioAndEstadoNot(idCancha, fecha, horaInicio, "Cancelada")) {
            throw new IllegalStateException("reserva.error02");
        }
        //Regla 3: el monto sale de la tarifa vigente (diurna o nocturna)
        Optional<BigDecimal> precio = getPrecio(idCancha, horaInicio);
        if (precio.isEmpty()) {
            throw new IllegalStateException("reserva.error05");
        }

        Reserva reserva = new Reserva();
        reserva.setCancha(cancha);
        reserva.setUsuario(usuario);
        reserva.setFecha(fecha);
        reserva.setHoraInicio(horaInicio);
        reserva.setHoraFin(horaFin);
        reserva.setMontoTotal(precio.get());
        reserva.setEstado("Pendiente");
        return reservaRepository.save(reserva);
    }

    //HU-13: registra el pago y confirma la reserva en una misma transacción (regla 4)
    //Devuelve true si la reserva quedó confirmada
    @Transactional
    public boolean pagar(Integer idReserva, String metodo) {
        Optional<Reserva> reservaOpt = reservaRepository.findById(idReserva);
        if (reservaOpt.isEmpty()) {
            throw new IllegalArgumentException("reserva.error01");
        }
        Reserva reserva = reservaOpt.get();
        if (!"Pendiente".equals(reserva.getEstado())) {
            throw new IllegalStateException("reserva.error06");
        }
        //Si ya había un pago pendiente (efectivo) se actualiza; si no, se crea uno nuevo
        Optional<Pago> pagoOpt = pagoRepository.findByReservaIdReserva(idReserva);
        Pago pago = pagoOpt.isPresent() ? pagoOpt.get() : new Pago();
        pago.setReserva(reserva);
        pago.setMonto(reserva.getMontoTotal());
        pago.setMetodo(metodo);
        if ("Efectivo".equals(metodo)) {
            //En efectivo se paga en el complejo: la reserva queda apartada como "Pendiente"
            pago.setEstado("Pendiente");
            pago.setFechaPago(null);
            pagoRepository.save(pago);
            return false;
        }
        pago.setEstado("Pagado");
        pago.setFechaPago(LocalDateTime.now());
        pagoRepository.save(pago);
        reserva.setEstado("Confirmada");
        reservaRepository.save(reserva);
        return true;
    }

    //HU-15: cancela hasta 24 horas antes y reembolsa el pago (regla 5)
    @Transactional
    public Reserva cancelar(Integer idReserva) {
        Optional<Reserva> reservaOpt = reservaRepository.findById(idReserva);
        if (reservaOpt.isEmpty()) {
            throw new IllegalArgumentException("reserva.error01");
        }
        Reserva reserva = reservaOpt.get();
        if ("Cancelada".equals(reserva.getEstado())) {
            throw new IllegalStateException("reserva.error06");
        }
        LocalDateTime inicio = reserva.getFecha().atTime(reserva.getHoraInicio());
        if (LocalDateTime.now().plusHours(24).isAfter(inicio)) {
            throw new IllegalStateException("reserva.error04");
        }
        reserva.setEstado("Cancelada");
        reservaRepository.save(reserva);
        Optional<Pago> pagoOpt = pagoRepository.findByReservaIdReserva(idReserva);
        if (pagoOpt.isPresent()) {
            Pago pago = pagoOpt.get();
            if ("Pagado".equals(pago.getEstado())) {
                pago.setEstado("Reembolsado");
            } else {
                pago.setEstado("Rechazado");
            }
            pagoRepository.save(pago);
        }
        return reserva;
    }

    //Canchas activas para el filtro de disponibilidad
    @Transactional(readOnly = true)
    public List<Cancha> getCanchasActivas() {
        return canchaRepository.findByActivoTrue();
    }

    @Transactional(readOnly = true)
    public Optional<Cancha> getCancha(Integer idCancha) {
        return canchaRepository.findById(idCancha);
    }

    //Clientes activos (mientras no se vea Spring Security, el cliente se elige de una lista)
    @Transactional(readOnly = true)
    public List<Usuario> getClientes() {
        return usuarioRepository.findByActivoTrue();
    }

    @Transactional(readOnly = true)
    public Optional<Reserva> getReserva(Integer idReserva) {
        return reservaRepository.findById(idReserva);
    }

    //HU-14: historial de reservas de un cliente
    @Transactional(readOnly = true)
    public List<Reserva> getReservasUsuario(Integer idUsuario) {
        return reservaRepository.findByUsuarioIdUsuarioOrderByFechaDescHoraInicioDesc(idUsuario);
    }

    //Reservas de una fecha (resumen del día)
    @Transactional(readOnly = true)
    public List<Reserva> getReservasDelDia(LocalDate fecha) {
        return reservaRepository.findByFechaOrderByHoraInicioAsc(fecha);
    }

    //HU-21: todas las reservas (vista del administrador)
    @Transactional(readOnly = true)
    public List<Reserva> getReservas() {
        return reservaRepository.findAllByOrderByFechaDescHoraInicioDesc();
    }
}
