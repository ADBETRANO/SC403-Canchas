package com.golazo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(unique = true, nullable = false, length = 30)
    @NotBlank
    @Size(max = 30)
    private String username;

    @Column(nullable = false, length = 512)
    private String password;

    @Column(nullable = false, length = 20)
    @NotBlank
    @Size(max = 20)
    private String nombre;

    @Column(nullable = false, length = 30)
    @NotBlank
    @Size(max = 30)
    private String apellidos;

    @Column(unique = true, nullable = false, length = 75)
    @NotBlank
    @Size(max = 75)
    private String correo;

    @Column(length = 25)
    @Size(max = 25)
    private String telefono;

    private boolean activo;

}
