package mx.edu.utez.pret.service;

import mx.edu.utez.pret.model.Candidato;
import mx.edu.utez.pret.model.Postulacion;
import mx.edu.utez.pret.model.PostulacionId;

import java.util.List;
import java.util.Optional;

public interface PostulacionService {
    Optional<Postulacion> obtenerPorId(PostulacionId id);
    Postulacion guardar(Postulacion postulacion);
    Boolean eliminar(PostulacionId id);
}
