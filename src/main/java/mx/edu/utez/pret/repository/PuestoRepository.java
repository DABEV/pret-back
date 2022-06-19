package mx.edu.utez.pret.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.edu.utez.pret.model.Puesto;

public interface PuestoRepository extends JpaRepository<Puesto, Long> {
}
