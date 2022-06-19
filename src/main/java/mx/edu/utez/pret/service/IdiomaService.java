package mx.edu.utez.pret.service;

import java.util.Optional;

import java.util.List;
import mx.edu.utez.pret.model.Idioma;

public interface IdiomaService {
    Optional<Idioma> obtenerPorId(Long id);
    List<Idioma> obtenerTodos();
}
