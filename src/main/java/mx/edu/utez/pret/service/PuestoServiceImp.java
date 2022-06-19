package mx.edu.utez.pret.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.utez.pret.model.Puesto;
import mx.edu.utez.pret.repository.PuestoRepository;

@Service
public class PuestoServiceImp implements PuestoService {

    @Autowired
    private PuestoRepository repository;

    @Override
    public Optional<Puesto> obtenerPorId(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Puesto> obtenerTodos() {
        return repository.findAll();
    }
}
