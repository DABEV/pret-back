package mx.edu.utez.pret.service;

import java.util.Optional;

import java.util.List;
import mx.edu.utez.pret.model.EstadoVacante;

public interface EstadoVacanteService {
    Optional<EstadoVacante> obtenerPorId(Long id);
    List<EstadoVacante> obtenerTodos();
}
