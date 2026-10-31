package com.golazo.repository;

import com.golazo.domain.Horario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Integer> {

    //Consulta derivada para listar los horarios ordenados por cancha
    public List<Horario> findAllByOrderByCanchaNombreAscDiaAsc();

    //Consulta derivada para obtener el horario de una cancha en un tipo de día
    public Optional<Horario> findFirstByCanchaIdCanchaAndDia(Integer idCancha, String dia);
}
