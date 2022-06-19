package mx.edu.utez.pret.service;

import java.util.Optional;

import mx.edu.utez.pret.model.Curso;

public interface CursoService {
    Optional<Curso> obtenerPorId(Long id);
    Curso guardar(Curso curso);
    Boolean eliminar(Long id);
}
