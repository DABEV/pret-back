package mx.edu.utez.pret.service;

import java.util.Optional;
import java.util.List;

import mx.edu.utez.pret.model.Rol;

public interface RolService {
    Optional<Rol> obtenerPorId(Long id);
    List<Rol> obtenerTodos();
    Rol buscarPorNombre(String nombre);
}
