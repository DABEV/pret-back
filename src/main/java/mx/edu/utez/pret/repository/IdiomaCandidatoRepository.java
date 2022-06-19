package mx.edu.utez.pret.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.edu.utez.pret.model.IdiomaCandidato;
import mx.edu.utez.pret.model.IdiomaCandidatoId;

public interface IdiomaCandidatoRepository extends JpaRepository<IdiomaCandidato, IdiomaCandidatoId> {
}
