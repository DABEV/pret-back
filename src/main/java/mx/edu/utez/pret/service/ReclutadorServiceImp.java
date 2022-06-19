package mx.edu.utez.pret.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.utez.pret.model.Reclutador;
import mx.edu.utez.pret.repository.ReclutadorRepository;

@Service
public class ReclutadorServiceImp implements ReclutadorService {

    @Autowired
    private ReclutadorRepository repository;

    @Override
    public Optional<Reclutador> obtenerPorId(Long id) {
        return repository.findById(id);
    }

    @Override
    public Reclutador guardar(Reclutador reclutador) {
        return repository.save(reclutador);
    }

    @Override
    public Boolean eliminar(Long id) {
        return obtenerPorId(id).map(reclutador -> {
            repository.deleteById(id);
            return true;
        }).orElse(false);
    }
    
}
