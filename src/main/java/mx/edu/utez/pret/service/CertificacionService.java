package mx.edu.utez.pret.service;

import java.util.List;
import java.util.Optional;

import mx.edu.utez.pret.model.Candidato;
import mx.edu.utez.pret.model.Certificacion;

public interface CertificacionService {
    Optional<Certificacion> obtenerPorId(Long id);
    Certificacion guardar(Certificacion certificacion);
    Boolean eliminar(Long id);

    List<Certificacion> obtenerPorCandidato(Candidato candidato);
}
