package mx.edu.utez.pret.service;

import java.util.Optional;

import mx.edu.utez.pret.model.Candidato;

public interface CandidatoService {
    Optional<Candidato> obtenerPorId(Long id);
    Candidato guardar(Candidato candidato);
    Boolean eliminar(Long id);
}
