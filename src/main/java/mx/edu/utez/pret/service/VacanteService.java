package mx.edu.utez.pret.service;

import java.util.Optional;

import mx.edu.utez.pret.model.Vacante;

public interface VacanteService {
    Optional<Vacante> obtenerPorId(Long id);
    Vacante guardar(Vacante vacante);
    Boolean eliminar(Long id);
}
