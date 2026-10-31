package com.golazo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalTime;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "horario")
public class Horario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_horario")
    private Integer idHorario;

    //Valores: "Lunes a viernes" o "Fin de semana"
    @Column(nullable = false, length = 20)
    @NotBlank
    private String dia;

    @NotNull
    private LocalTime horaApertura;

    @NotNull
    private LocalTime horaCierre;

    @ManyToOne
    @JoinColumn(name = "id_cancha")
    private Cancha cancha;

}
