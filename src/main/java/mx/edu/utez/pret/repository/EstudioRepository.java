package mx.edu.utez.pret.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.edu.utez.pret.model.Estudio;

public interface EstudioRepository extends JpaRepository<Estudio, Long> {
}
