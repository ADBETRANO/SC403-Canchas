package com.golazo.controller;

import com.golazo.domain.Reserva;
import com.golazo.service.ReservaService;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reserva")
public class ReservaController {

    //Se usa el servicio de reservas
    private final ReservaService reservaService;
    private final MessageSource messageSource;

    //  HU-11: http://localhost/reserva/disponibilidad?idCancha=1&fecha=2026-11-05
    @GetMapping("/disponibilidad")
    public String disponibilidad(@RequestParam(required = false) Integer idCancha,
            @RequestParam(required = false) LocalDate fecha,
            Model model) {
        if (fecha == null) {
            fecha = LocalDate.now();
        }
        model.addAttribute("canchas", reservaService.getCanchasActivas());
        model.addAttribute("hoy", LocalDate.now());
        model.addAttribute("manana", LocalDate.now().plusDays(1));
        model.addAttribute("pasado", LocalDate.now().plusDays(2));
        model.addAttribute("fecha", fecha);
        model.addAttribute("idCancha", idCancha);
        if (idCancha != null) {
            model.addAttribute("cancha", reservaService.getCancha(idCancha).orElse(null));
            model.addAttribute("tipoDia", reservaService.getTipoDia(fecha));
            model.addAttribute("horario", reservaService.getHorario(idCancha, fecha).orElse(null));
            //Tarifa diurna (antes de las 18:00) y nocturna (desde las 18:00)
            model.addAttribute("precioDiurno", reservaService.getPrecio(idCancha, LocalTime.of(8, 0)).orElse(null));
            model.addAttribute("precioNocturno", reservaService.getPrecio(idCancha, LocalTime.of(18, 0)).orElse(null));
            var espacios = reservaService.getDisponibilidad(idCancha, fecha);
            int disponibles = 0;
            for (var e : espacios) {
                if (e.isDisponible()) {
                    disponibles++;
                }
            }
            model.addAttribute("espacios", espacios);
            model.addAttribute("disponibles", disponibles);
            model.addAttribute("ocupados", espacios.size() - disponibles);
        }
        return "reserva/disponibilidad";
    }

    //  HU-12: pantalla para confirmar la reserva del espacio elegido
    @GetMapping("/nueva")
    public String nueva(@RequestParam Integer idCancha,
            @RequestParam LocalDate fecha,
            @RequestParam LocalTime horaInicio,
            Model model, RedirectAttributes redirectAttributes) {
        var cancha = reservaService.getCancha(idCancha);
        var precio = reservaService.getPrecio(idCancha, horaInicio);
        if (cancha.isEmpty() || precio.isEmpty()) {
            redirectAttributes.addFlashAttribute("error",
                    messageSource.getMessage("reserva.error03", null, Locale.getDefault()));
            return "redirect:/reserva/disponibilidad";
        }
        model.addAttribute("cancha", cancha.get());
        model.addAttribute("fecha", fecha);
        model.addAttribute("horaInicio", horaInicio);
        model.addAttribute("horaFin", horaInicio.plusHours(1));
        model.addAttribute("tipoTarifa", reservaService.getTipoTarifa(horaInicio));
        model.addAttribute("precio", precio.get());
        //Mientras no se vea Spring Security, el cliente se elige de una lista
        model.addAttribute("usuarios", reservaService.getClientes());
        return "reserva/nueva";
    }

    @PostMapping("/guardar")
    public String guardar(@RequestParam Integer idCancha, @RequestParam Integer idUsuario,
            @RequestParam LocalDate fecha,
            @RequestParam LocalTime horaInicio,
            RedirectAttributes redirectAttributes) {
        try {
            var reserva = reservaService.crearReserva(idCancha, idUsuario, fecha, horaInicio);
            redirectAttributes.addFlashAttribute("todoOk",
                    messageSource.getMessage("reserva.creada", null, Locale.getDefault()));
            return "redirect:/reserva/pago/" + reserva.getIdReserva();
        } catch (IllegalArgumentException | IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error",
                    messageSource.getMessage(e.getMessage(), null, Locale.getDefault()));
            return "redirect:/reserva/disponibilidad?idCancha=" + idCancha + "&fecha=" + fecha;
        }
    }

    //  HU-13: pantalla de pago de una reserva
    @GetMapping("/pago/{idReserva}")
    public String pago(@PathVariable Integer idReserva, Model model, RedirectAttributes redirectAttributes) {
        var reserva = reservaService.getReserva(idReserva);
        if (reserva.isEmpty()) {
            redirectAttributes.addFlashAttribute("error",
                    messageSource.getMessage("reserva.error01", null, Locale.getDefault()));
            return "redirect:/reserva/disponibilidad";
        }
        model.addAttribute("reserva", reserva.get());
        return "reserva/pago";
    }

    @PostMapping("/pagar")
    public String pagar(@RequestParam Integer idReserva, @RequestParam String metodo,
            RedirectAttributes redirectAttributes) {
        try {
            boolean confirmada = reservaService.pagar(idReserva, metodo);
            String mensaje = confirmada ? "reserva.pagada" : "reserva.efectivo";
            redirectAttributes.addFlashAttribute("todoOk",
                    messageSource.getMessage(mensaje, null, Locale.getDefault()));
            var reserva = reservaService.getReserva(idReserva).get();
            return "redirect:/reserva/misReservas?idUsuario=" + reserva.getUsuario().getIdUsuario();
        } catch (IllegalArgumentException | IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error",
                    messageSource.getMessage(e.getMessage(), null, Locale.getDefault()));
            return "redirect:/reserva/pago/" + idReserva;
        }
    }

    //  HU-14: historial de reservas del cliente
    @GetMapping("/misReservas")
    public String misReservas(@RequestParam(required = false) Integer idUsuario,
            @RequestParam(required = false) String estado, Model model) {
        model.addAttribute("usuarios", reservaService.getClientes());
        model.addAttribute("idUsuario", idUsuario);
        model.addAttribute("estado", estado);
        if (idUsuario != null) {
            //Filtro por estado: Todas, Confirmada, Pendiente o Cancelada
            List<Reserva> reservas = new ArrayList<>();
            for (Reserva r : reservaService.getReservasUsuario(idUsuario)) {
                if (estado == null || estado.equals(r.getEstado())) {
                    reservas.add(r);
                }
            }
            model.addAttribute("reservas", reservas);
        }
        return "reserva/misReservas";
    }

    //  HU-15: cancelar una reserva
    @PostMapping("/cancelar")
    public String cancelar(@RequestParam Integer idReserva, @RequestParam Integer idUsuario,
            RedirectAttributes redirectAttributes) {
        try {
            reservaService.cancelar(idReserva);
            redirectAttributes.addFlashAttribute("todoOk",
                    messageSource.getMessage("reserva.cancelada", null, Locale.getDefault()));
        } catch (IllegalArgumentException | IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error",
                    messageSource.getMessage(e.getMessage(), null, Locale.getDefault()));
        }
        return "redirect:/reserva/misReservas?idUsuario=" + idUsuario;
    }

}
