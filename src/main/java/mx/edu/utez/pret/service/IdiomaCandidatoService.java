package mx.edu.utez.pret.service;

import java.util.Optional;

import mx.edu.utez.pret.model.IdiomaCandidato;
import mx.edu.utez.pret.model.IdiomaCandidatoId;

public interface IdiomaCandidatoService {
    Optional<IdiomaCandidato> obtenerPorId(IdiomaCandidatoId id);
    IdiomaCandidato guardar(IdiomaCandidato contacto);
    Boolean eliminar(IdiomaCandidatoId id);
    Boolean deleteByCandidatoIdAndidiomaId(long candidato, long id);
}
