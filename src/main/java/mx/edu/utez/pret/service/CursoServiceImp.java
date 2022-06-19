package mx.edu.utez.pret.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.utez.pret.model.Curso;
import mx.edu.utez.pret.repository.CursoRepository;

@Service
public class CursoServiceImp implements CursoService {

    @Autowired
    private CursoRepository repository;

    @Override
    public Optional<Curso> obtenerPorId(Long id) {
        return repository.findById(id);
    }

    @Override
    public Curso guardar(Curso curso) {
        return repository.save(curso);
    }

    @Override
    public Boolean eliminar(Long id) {
        return obtenerPorId(id).map(curso -> {
            repository.deleteById(id);
            return true;
        }).orElse(false);
    }
    
}
