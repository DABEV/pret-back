package mx.edu.utez.pret.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.edu.utez.pret.model.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> {
}
