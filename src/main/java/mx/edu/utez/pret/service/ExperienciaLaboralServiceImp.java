package mx.edu.utez.pret.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.utez.pret.model.ExperienciaLaboral;
import mx.edu.utez.pret.repository.ExperienciaLaboralRepository;

@Service
public class ExperienciaLaboralServiceImp implements ExperienciaLaboralService {

    @Autowired
    private ExperienciaLaboralRepository repository;

    @Override
    public Optional<ExperienciaLaboral> obtenerPorId(Long id) {
        return repository.findById(id);
    }

    @Override
    public ExperienciaLaboral guardar(ExperienciaLaboral experienciaLaboral) {
        return repository.save(experienciaLaboral);
    }

    @Override
    public Boolean eliminar(Long id) {
        return obtenerPorId(id).map(experienciaLaboral -> {
            repository.deleteById(id);
            return true;
        }).orElse(false);
    }
    
}
