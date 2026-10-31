package com.golazo.service;

import com.golazo.domain.TipoCancha;
import com.golazo.repository.TipoCanchaRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TipoCanchaService {

    //Utilizamos un objeto tipoCanchaRepository para interactuar con la BD
    private final TipoCanchaRepository tipoCanchaRepository;

    //Se recupera la lista de registros de la tabla tipo_cancha
    @Transactional(readOnly = true)
    public List<TipoCancha> getTiposCancha(boolean activo) {
        if (activo) { //Si solo se quieren los tipos activos...
            return tipoCanchaRepository.findByActivoTrue();
        } else {
            return tipoCanchaRepository.findAll();
        }
    }

    @Transactional(readOnly = true)
    public Optional<TipoCancha> getTipoCancha(Integer idTipoCancha) {
        return tipoCanchaRepository.findById(idTipoCancha);
    }

    @Transactional
    public void save(TipoCancha tipoCancha) {
        tipoCanchaRepository.save(tipoCancha);
    }

    @Transactional
    public void delete(Integer idTipoCancha) {
        // Verifica si el tipo de cancha existe antes de intentar eliminarlo
        if (!tipoCanchaRepository.existsById(idTipoCancha)) {
            throw new IllegalArgumentException("El tipo de cancha con ID " + idTipoCancha + " no existe.");
        }
        try {
            tipoCanchaRepository.deleteById(idTipoCancha);
            tipoCanchaRepository.flush();
        } catch (DataIntegrityViolationException e) {
            // Tiene canchas asociadas
            throw new IllegalStateException("No se puede eliminar el tipo de cancha. Tiene datos asociados.", e);
        }
    }
}
