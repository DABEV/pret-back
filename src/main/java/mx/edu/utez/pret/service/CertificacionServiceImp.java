package mx.edu.utez.pret.service;

import java.util.List;
import java.util.Optional;

import mx.edu.utez.pret.model.Candidato;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.utez.pret.model.Certificacion;
import mx.edu.utez.pret.repository.CertificacionRepository;

@Service
public class CertificacionServiceImp implements CertificacionService {

    @Autowired
    private CertificacionRepository repository;

    @Override
    public Optional<Certificacion> obtenerPorId(Long id) {
        return repository.findById(id);
    }

    @Override
    public Certificacion guardar(Certificacion certificacion) {
        return repository.save(certificacion);
    }

    @Override
    public Boolean eliminar(Long id) {
        return obtenerPorId(id).map(certificacion -> {
            repository.deleteById(id);
            return true;
        }).orElse(false);
    }

    @Override
    public List<Certificacion> obtenerPorCandidato(Candidato candidato) {
        return repository.findByCandidato(candidato);
    }

}
