package mx.edu.utez.pret.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.utez.pret.model.Reclutador;
import mx.edu.utez.pret.model.Vacante;
import mx.edu.utez.pret.repository.VacanteRepository;

@Service
public class VacanteServiceImp implements VacanteService {

    @Autowired
    private VacanteRepository repository;

    @Override
    public Optional<Vacante> obtenerPorId(Long id) {
        return repository.findById(id);
    }

    @Override
    public Vacante guardar(Vacante vacante) {
        return repository.save(vacante);
    }

    @Override
    public Boolean eliminar(Long id) {
        return obtenerPorId(id).map(vacante -> {
            repository.deleteById(id);
            return true;
        }).orElse(false);
    }

    @Override
    public List<Vacante> obtenerTodos(LocalDate fechaVigencia) {
        return repository.findAllVacantesWithActualDate(fechaVigencia);
    }

    @Override
    public List<Vacante> obtenerPorReclutador(Reclutador reclutador) {
        return repository.findByReclutador(reclutador);
    }
    
}
