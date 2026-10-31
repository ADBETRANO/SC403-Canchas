package com.golazo.repository;

import com.golazo.domain.TipoCancha;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoCanchaRepository extends JpaRepository<TipoCancha, Integer> {

    //Consulta derivada para recuperar solo los tipos de cancha activos
    public List<TipoCancha> findByActivoTrue();
}
