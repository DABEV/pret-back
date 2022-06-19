package mx.edu.utez.pret.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.edu.utez.pret.model.EstadoVacante;

public interface EstadoVacanteRepository extends JpaRepository<EstadoVacante, Long> {
}
