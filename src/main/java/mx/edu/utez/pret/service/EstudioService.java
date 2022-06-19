package mx.edu.utez.pret.service;

import java.util.Optional;

import mx.edu.utez.pret.model.Estudio;

public interface EstudioService {
    Optional<Estudio> obtenerPorId(Long id);
    Estudio guardar(Estudio estudio);
    Boolean eliminar(Long id);
}
