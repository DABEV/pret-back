package mx.edu.utez.pret.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.utez.pret.model.EstadoVacante;
import mx.edu.utez.pret.repository.EstadoVacanteRepository;

@Service
public class EstadoVacanteServiceImp implements EstadoVacanteService {

    @Autowired
    private EstadoVacanteRepository repository;

    @Override
    public Optional<EstadoVacante> obtenerPorId(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<EstadoVacante> obtenerTodos() {
        return repository.findAll();
    }
}
