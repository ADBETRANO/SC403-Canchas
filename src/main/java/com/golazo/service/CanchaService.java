package com.golazo.service;

import com.golazo.domain.Cancha;
import com.golazo.domain.TipoCancha;
import com.golazo.repository.CanchaRepository;
import com.golazo.repository.TipoCanchaRepository;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class CanchaService {

    //Utilizamos un objeto canchaRepository para interactuar con la BD
    private final CanchaRepository canchaRepository;
    private final TipoCanchaRepository tipoCanchaRepository;
    private final FirebaseStorageService firebaseStorageService;

    //Se recupera la lista de registros de la tabla cancha
    @Transactional(readOnly = true)
    public List<Cancha> getCanchas(boolean activo) {
        if (activo) { //Si solo se quieren las canchas activas...
            return canchaRepository.findByActivoTrue();
        } else {
            return canchaRepository.findAll();
        }
    }

    //Catálogo: canchas activas de un tipo (HU-09)
    @Transactional(readOnly = true)
    public List<Cancha> getCanchasPorTipo(Integer idTipoCancha) {
        return canchaRepository.findByTipoCanchaIdTipoCanchaAndActivoTrue(idTipoCancha);
    }

    //Tipos de cancha activos para el formulario y los filtros del catálogo
    @Transactional(readOnly = true)
    public List<TipoCancha> getTiposCancha() {
        return tipoCanchaRepository.findByActivoTrue();
    }

    @Transactional(readOnly = true)
    public Optional<Cancha> getCancha(Integer idCancha) {
        return canchaRepository.findById(idCancha);
    }

    @Transactional
    public void save(Cancha cancha, MultipartFile imagenFile) {
        cancha = canchaRepository.save(cancha);
        if (imagenFile != null && !imagenFile.isEmpty()) { //Si no está vacío... pasaron una imagen...
            try {
                String rutaImagen = firebaseStorageService.uploadImage(
                        imagenFile, "cancha",
                        cancha.getIdCancha());
                cancha.setRutaImagen(rutaImagen);
                canchaRepository.save(cancha);
            } catch (IOException e) {
                log.warn("No fue posible almacenar la imagen de la cancha {}", cancha.getIdCancha(), e);
            }
        }
    }

    @Transactional
    public void delete(Integer idCancha) {
        // Verifica si la cancha existe antes de intentar eliminarla
        if (!canchaRepository.existsById(idCancha)) {
            throw new IllegalArgumentException("La cancha con ID " + idCancha + " no existe.");
        }
        try {
            canchaRepository.deleteById(idCancha);
            canchaRepository.flush();
        } catch (DataIntegrityViolationException e) {
            // Regla 7: una cancha con reservas no se elimina, solo se desactiva
            throw new IllegalStateException("No se puede eliminar la cancha. Tiene datos asociados.", e);
        }
    }
}
