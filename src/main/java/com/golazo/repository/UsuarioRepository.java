package com.golazo.repository;

import com.golazo.domain.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    //Consulta derivada para recuperar solo los usuarios activos
    public List<Usuario> findByActivoTrue();

    //Consulta derivada para validar que el username y el correo sean únicos
    public boolean existsByUsernameOrCorreo(String username, String correo);
}
