package com.golazo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tipo_cancha")
public class TipoCancha implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_cancha")
    private Integer idTipoCancha;

    @Column(unique = true, nullable = false, length = 30)
    @NotBlank
    @Size(max = 30)
    private String descripcion;

    private boolean activo;

    @OneToMany(mappedBy = "tipoCancha")
    private List<Cancha> canchas;

}
