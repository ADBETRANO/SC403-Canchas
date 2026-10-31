package com.golazo.repository;

import com.golazo.domain.Tarifa;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarifaRepository extends JpaRepository<Tarifa, Integer> {

    //Consulta derivada para listar las tarifas ordenadas por cancha
    public List<Tarifa> findAllByOrderByCanchaNombreAscTipoTarifaAsc();

    //Consulta derivada para obtener la tarifa vigente de una cancha (Diurna o Nocturna)
    public Optional<Tarifa> findFirstByCanchaIdCanchaAndTipoTarifaAndVigenteTrue(Integer idCancha, String tipoTarifa);
}
