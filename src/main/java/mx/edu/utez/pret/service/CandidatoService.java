package mx.edu.utez.pret.service;

import java.util.Optional;

import mx.edu.utez.pret.model.Candidato;
import mx.edu.utez.pret.model.Usuario;

public interface CandidatoService {
    Optional<Candidato> obtenerPorId(Long id);
    Optional<Usuario> obtenerPorCorreo(String correoElectronico);
    Candidato guardar(Candidato candidato);
    Boolean eliminar(Long id);
}
