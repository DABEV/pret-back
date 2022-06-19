package mx.edu.utez.pret.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.edu.utez.pret.model.Candidato;

public interface CandidatoRepository extends JpaRepository<Candidato, Long> {
}
