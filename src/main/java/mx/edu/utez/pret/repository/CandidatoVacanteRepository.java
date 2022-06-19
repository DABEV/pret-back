package mx.edu.utez.pret.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.edu.utez.pret.model.CandidatoVacante;
import mx.edu.utez.pret.model.CandidatoVacanteId;

public interface CandidatoVacanteRepository extends JpaRepository<CandidatoVacante, CandidatoVacanteId> {
}
