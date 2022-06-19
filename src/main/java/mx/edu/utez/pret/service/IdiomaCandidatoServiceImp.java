package mx.edu.utez.pret.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.utez.pret.model.IdiomaCandidato;
import mx.edu.utez.pret.model.IdiomaCandidatoId;
import mx.edu.utez.pret.repository.IdiomaCandidatoRepository;

@Service
public class IdiomaCandidatoServiceImp implements IdiomaCandidatoService {

    @Autowired
    private IdiomaCandidatoRepository repository;

    @Override
    public Optional<IdiomaCandidato> obtenerPorId(IdiomaCandidatoId id) {
        return repository.findById(id);
    }

    @Override
    public IdiomaCandidato guardar(IdiomaCandidato idiomaCandidato) {
        return repository.save(idiomaCandidato);
    }

    @Override
    public Boolean eliminar(IdiomaCandidatoId id) {
        return obtenerPorId(id).map(idiomaCandidato -> {
            repository.deleteById(id);
            return true;
        }).orElse(false);
    }
    
}
