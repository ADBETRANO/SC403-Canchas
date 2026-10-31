package com.golazo.repository;

import com.golazo.domain.Reserva;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {

    //Reservas de una cancha en una fecha, sin contar las canceladas (para la disponibilidad)
    public List<Reserva> findByCanchaIdCanchaAndFechaAndEstadoNot(Integer idCancha, LocalDate fecha, String estado);

    //Regla 1: una cancha no puede tener dos reservas en la misma fecha y hora
    public boolean existsByCanchaIdCanchaAndFechaAndHoraInicioAndEstadoNot(Integer idCancha, LocalDate fecha, LocalTime horaInicio, String estado);

    //Historial de reservas de un usuario (Mis reservas)
    public List<Reserva> findByUsuarioIdUsuarioOrderByFechaDescHoraInicioDesc(Integer idUsuario);

    //Reservas de un día, ordenadas por hora (resumen del día)
    public List<Reserva> findByFechaOrderByHoraInicioAsc(LocalDate fecha);

    //Todas las reservas, de la más reciente a la más antigua
    public List<Reserva> findAllByOrderByFechaDescHoraInicioDesc();
}
