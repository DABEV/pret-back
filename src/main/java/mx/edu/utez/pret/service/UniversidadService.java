package mx.edu.utez.pret.service;

import java.util.Optional;

import java.util.List;
import mx.edu.utez.pret.model.Universidad;

public interface UniversidadService {
    Optional<Universidad> obtenerPorId(Long id);
    List<Universidad> obtenerTodos();
}
