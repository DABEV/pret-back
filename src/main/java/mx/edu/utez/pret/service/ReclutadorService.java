package mx.edu.utez.pret.service;

import java.util.Optional;

import mx.edu.utez.pret.model.Reclutador;

public interface ReclutadorService {
    Optional<Reclutador> obtenerPorId(Long id);
    Reclutador guardar(Reclutador reclutador);
    Boolean eliminar(Long id);
}
