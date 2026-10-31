package com.golazo.domain;

import java.math.BigDecimal;
import java.time.LocalTime;
import lombok.*;

//Espacio de una hora en la agenda de una cancha (no es una tabla, solo se usa para mostrar la disponibilidad)
@Getter
@Setter
@NoArgsConstructor
public class Espacio {

    private LocalTime inicio;
    private LocalTime fin;
    private boolean disponible;
    private String tipoTarifa;
    private BigDecimal precio;

}
