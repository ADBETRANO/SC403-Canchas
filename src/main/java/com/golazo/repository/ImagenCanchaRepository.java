package com.golazo.repository;

import com.golazo.domain.ImagenCancha;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagenCanchaRepository extends JpaRepository<ImagenCancha, Integer> {

    //Consulta derivada para recuperar la galería de imágenes de una cancha
    public List<ImagenCancha> findByCanchaIdCancha(Integer idCancha);
}
