package mx.edu.utez.pret.service;

import java.util.Optional;

import java.util.List;
import mx.edu.utez.pret.model.Beneficio;

public interface BeneficioService {
    Optional<Beneficio> obtenerPorId(Long id);
    List<Beneficio> obtenerTodos();
}
