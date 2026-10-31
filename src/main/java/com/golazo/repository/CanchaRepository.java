package com.golazo.repository;

import com.golazo.domain.Cancha;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CanchaRepository extends JpaRepository<Cancha, Integer> {

    //Consulta derivada para recuperar solo las canchas activas
    public List<Cancha> findByActivoTrue();

    //Consulta derivada para el catálogo filtrado por tipo de cancha (solo activas)
    public List<Cancha> findByTipoCanchaIdTipoCanchaAndActivoTrue(Integer idTipoCancha);
}
