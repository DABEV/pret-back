package mx.edu.utez.pret.service;

import java.util.Optional;

import mx.edu.utez.pret.model.Certificacion;

public interface CertificacionService {
    Optional<Certificacion> obtenerPorId(Long id);
    Certificacion guardar(Certificacion certificacion);
    Boolean eliminar(Long id);
}
