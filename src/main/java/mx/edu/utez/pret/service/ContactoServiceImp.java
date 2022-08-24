package mx.edu.utez.pret.service;

import java.util.List;
import java.util.Optional;

import mx.edu.utez.pret.model.Candidato;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.utez.pret.model.Contacto;
import mx.edu.utez.pret.model.ContactoId;
import mx.edu.utez.pret.repository.ContactoRepository;

@Service
public class ContactoServiceImp implements ContactoService {

    @Autowired
    private ContactoRepository repository;

    @Override
    public Optional<Contacto> obtenerPorId(ContactoId id) {
        return repository.findById(id);
    }

    @Override
    public Contacto guardar(Contacto contacto) {
        return repository.save(contacto);
    }

    @Override
    public Boolean eliminar(ContactoId id) {
        return obtenerPorId(id).map(contacto -> {
            repository.deleteById(id);
            return true;
        }).orElse(false);
    }

    @Override
    public List<Contacto> obtenerPorCandidato(Candidato candidato) {
        return repository.findByCandidato(candidato);
    }

    @Override
    public List<Contacto> obtenerPorAmigo(Candidato candidato) {
        return repository.findByAmigo(candidato);
    }
}
