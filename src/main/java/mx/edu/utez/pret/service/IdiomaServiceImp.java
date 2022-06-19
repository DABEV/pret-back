package mx.edu.utez.pret.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.utez.pret.model.Idioma;
import mx.edu.utez.pret.repository.IdiomaRepository;

@Service
public class IdiomaServiceImp implements IdiomaService {

    @Autowired
    private IdiomaRepository repository;

    @Override
    public Optional<Idioma> obtenerPorId(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Idioma> obtenerTodos() {
        return repository.findAll();
    }
}
