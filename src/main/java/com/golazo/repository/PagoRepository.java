package com.golazo.repository;

import com.golazo.domain.Pago;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Integer> {

    //Consulta derivada para recuperar el pago de una reserva
    public Optional<Pago> findByReservaIdReserva(Integer idReserva);
}
