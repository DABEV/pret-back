package mx.edu.utez.pret.service;

import java.util.Optional;

import mx.edu.utez.pret.model.ExperienciaLaboral;

public interface ExperienciaLaboralService {
    Optional<ExperienciaLaboral> obtenerPorId(Long id);
    ExperienciaLaboral guardar(ExperienciaLaboral experienciaLaboral);
    Boolean eliminar(Long id);
}
