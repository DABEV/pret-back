package mx.edu.utez.pret.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.edu.utez.pret.model.Candidato;

import java.util.Optional;

public interface CandidatoRepository extends JpaRepository<Candidato, Long> {
}
