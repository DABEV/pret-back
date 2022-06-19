package mx.edu.utez.pret.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.utez.pret.model.Beneficio;
import mx.edu.utez.pret.repository.BeneficioRepository;

@Service
public class BeneficioServiceImp implements BeneficioService {

    @Autowired
    private BeneficioRepository repository;

    @Override
    public Optional<Beneficio> obtenerPorId(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Beneficio> obtenerTodos() {
        return repository.findAll();
    }
}
