package mx.edu.utez.pret.repository;

import mx.edu.utez.pret.model.Candidato;
import org.springframework.data.jpa.repository.JpaRepository;

import mx.edu.utez.pret.model.Curso;

import java.util.List;

public interface CursoRepository extends JpaRepository<Curso, Long> {
    List<Curso> findByCandidato(Candidato candidato);
}
