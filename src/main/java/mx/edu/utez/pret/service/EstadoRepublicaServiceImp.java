package mx.edu.utez.pret.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.utez.pret.model.EstadoRepublica;
import mx.edu.utez.pret.repository.EstadoRepublicaRepository;

@Service
public class EstadoRepublicaServiceImp implements EstadoRepublicaService {

    @Autowired
    private EstadoRepublicaRepository repository;

    @Override
    public Optional<EstadoRepublica> obtenerPorId(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<EstadoRepublica> obtenerTodos() {
        return repository.findAll();
    }
}
