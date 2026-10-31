package com.golazo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "cancha")
public class Cancha implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cancha")
    private Integer idCancha;

    @Column(unique = true, nullable = false, length = 50)
    @NotBlank
    @Size(max = 50)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(length = 30)
    @Size(max = 30)
    private String superficie;

    @NotNull
    @Min(value = 1)
    private Integer capacidad;

    @Column(length = 1024)
    @Size(max = 1024)
    private String rutaImagen;

    private boolean activo;

    @ManyToOne
    @JoinColumn(name = "id_tipo_cancha")
    private TipoCancha tipoCancha;

    //Tarifas y horarios de la cancha (se usan para mostrar precio y horario en el catálogo)
    @OneToMany(mappedBy = "cancha")
    private List<Tarifa> tarifas;

    @OneToMany(mappedBy = "cancha")
    private List<Horario> horarios;

}
