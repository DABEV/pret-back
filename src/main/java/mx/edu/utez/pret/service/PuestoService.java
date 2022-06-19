package mx.edu.utez.pret.service;

import java.util.Optional;

import java.util.List;
import mx.edu.utez.pret.model.Puesto;

public interface PuestoService {
    Optional<Puesto> obtenerPorId(Long id);
    List<Puesto> obtenerTodos();
}
