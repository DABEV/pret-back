package mx.edu.utez.pret.service;

import java.util.Optional;

import java.util.List;
import mx.edu.utez.pret.model.EstadoRepublica;

public interface EstadoRepublicaService {
    Optional<EstadoRepublica> obtenerPorId(Long id);
    List<EstadoRepublica> obtenerTodos();
}
