package mx.edu.utez.pret.service;

import mx.edu.utez.pret.model.Candidato;
import mx.edu.utez.pret.model.Postulacion;
import mx.edu.utez.pret.model.PostulacionId;
import mx.edu.utez.pret.repository.PostulacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostulacionServiceImp implements PostulacionService {
    @Autowired
    private PostulacionRepository repository;

    @Override
    public Optional<Postulacion> obtenerPorId(PostulacionId id) {
        return repository.findById(id);
    }

    @Override
    public Postulacion guardar(Postulacion postulacion) {
        return repository.save(postulacion);
    }

    @Override
    public Boolean eliminar(PostulacionId id) {
        return obtenerPorId(id).map(postulacion -> {
            repository.deleteById(id);
            return true;
        }).orElse(false);
    }

    @Override
    public List<Postulacion> obtenerPorCandidato(Candidato candidato) {
        return repository.findByCandidato(candidato);
    }
}
