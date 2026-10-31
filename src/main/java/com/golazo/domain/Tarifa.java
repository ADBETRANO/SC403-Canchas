package com.golazo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tarifa")
public class Tarifa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tarifa")
    private Integer idTarifa;

    //Valores: "Diurna" (antes de las 18:00) o "Nocturna" (desde las 18:00)
    @Column(nullable = false, length = 10)
    @NotBlank
    private String tipoTarifa;

    @Column(precision = 12, scale = 2)
    @NotNull
    @DecimalMin(value = "0.00", inclusive = true)
    private BigDecimal precioHora;

    private boolean vigente;

    @ManyToOne
    @JoinColumn(name = "id_cancha")
    private Cancha cancha;

}
