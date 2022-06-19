package mx.edu.utez.pret.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.edu.utez.pret.model.Vacante;

public interface VacanteRepository extends JpaRepository<Vacante, Long> {
}
