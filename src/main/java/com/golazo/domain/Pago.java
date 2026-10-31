package com.golazo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "pago")
public class Pago implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago")
    private Integer idPago;

    @Column(precision = 12, scale = 2, nullable = false)
    private BigDecimal monto;

    //Valores: "Tarjeta", "SINPE Móvil" o "Efectivo"
    @Column(nullable = false, length = 20)
    private String metodo;

    //Valores: "Pendiente", "Pagado", "Rechazado" o "Reembolsado"
    @Column(nullable = false, length = 15)
    private String estado;

    private LocalDateTime fechaPago;

    @OneToOne
    @JoinColumn(name = "id_reserva")
    private Reserva reserva;

}
