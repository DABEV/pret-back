package mx.edu.utez.pret.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.utez.pret.model.Estudio;
import mx.edu.utez.pret.repository.EstudioRepository;

@Service
public class EstudioServiceImp implements EstudioService {

    @Autowired
    private EstudioRepository repository;

    @Override
    public Optional<Estudio> obtenerPorId(Long id) {
        return repository.findById(id);
    }

    @Override
    public Estudio guardar(Estudio estudio) {
        return repository.save(estudio);
    }

    @Override
    public Boolean eliminar(Long id) {
        return obtenerPorId(id).map(estudio -> {
            repository.deleteById(id);
            return true;
        }).orElse(false);
    }

}
