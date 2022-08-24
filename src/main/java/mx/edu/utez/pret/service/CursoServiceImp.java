package mx.edu.utez.pret.service;

import java.util.List;
import java.util.Optional;

import mx.edu.utez.pret.model.Candidato;
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

    @Override
    public List<Curso> obtenerPorCandidato(Candidato candidato) {
        return repository.findByCandidato(candidato);
    }

}
