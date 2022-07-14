package mx.edu.utez.pret.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.edu.utez.pret.model.Postulacion;
import mx.edu.utez.pret.model.PostulacionId;

public interface CandidatoVacanteRepository extends JpaRepository<Postulacion, PostulacionId> {
}
