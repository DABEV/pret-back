package mx.edu.utez.pret.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.utez.pret.model.Rol;
import mx.edu.utez.pret.repository.RolRepository;

@Service
public class RolServiceImp implements RolService {

    @Autowired
    private RolRepository repository;

    @Override
    public Optional<Rol> obtenerPorId(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Rol> obtenerTodos() {
        return repository.findAll();
    }
}
