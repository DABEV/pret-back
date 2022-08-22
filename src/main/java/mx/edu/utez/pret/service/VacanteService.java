package mx.edu.utez.pret.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import mx.edu.utez.pret.model.Reclutador;
import mx.edu.utez.pret.model.Vacante;

public interface VacanteService {
    List<Vacante> obtenerTodos(LocalDate fechaVigencia);
    List<Vacante> obtenerPorReclutador(Reclutador reclutador);
    Optional<Vacante> obtenerPorId(Long id);
    Vacante guardar(Vacante vacante);
    Boolean eliminar(Long id);
}
