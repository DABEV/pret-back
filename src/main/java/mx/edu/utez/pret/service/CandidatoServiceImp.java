package mx.edu.utez.pret.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.utez.pret.model.Candidato;
import mx.edu.utez.pret.repository.CandidatoRepository;

@Service
public class CandidatoServiceImp implements CandidatoService {

    @Autowired
    private CandidatoRepository repository;

    @Override
    public Optional<Candidato> obtenerPorId(Long id) {
        return repository.findById(id);
    }

    @Override
    public Candidato guardar(Candidato candidato) {
        return repository.save(candidato);
    }

    @Override
    public Boolean eliminar(Long id) {
        return obtenerPorId(id).map(candidato -> {
            repository.deleteById(id);
            return true;
        }).orElse(false);
    }
    
}
