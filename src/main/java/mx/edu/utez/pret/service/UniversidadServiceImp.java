package mx.edu.utez.pret.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.utez.pret.model.Universidad;
import mx.edu.utez.pret.repository.UniversidadRepository;

@Service
public class UniversidadServiceImp implements UniversidadService {

    @Autowired
    private UniversidadRepository repository;

    @Override
    public Optional<Universidad> obtenerPorId(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Universidad> obtenerTodos() {
        return repository.findAll();
    }
}
