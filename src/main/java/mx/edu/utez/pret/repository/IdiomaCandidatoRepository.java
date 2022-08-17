package mx.edu.utez.pret.repository;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;

import mx.edu.utez.pret.model.IdiomaCandidato;
import mx.edu.utez.pret.model.IdiomaCandidatoId;

public interface IdiomaCandidatoRepository extends JpaRepository<IdiomaCandidato, IdiomaCandidatoId> {

    Optional<IdiomaCandidato> findByCandidatoIdAndIdiomaId(long candidatoId, long idiomaId);
}
