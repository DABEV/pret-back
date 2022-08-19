package mx.edu.utez.pret.repository;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;

import mx.edu.utez.pret.model.Candidato;
import mx.edu.utez.pret.model.Idioma;
import mx.edu.utez.pret.model.IdiomaCandidato;
import mx.edu.utez.pret.model.IdiomaCandidatoId;

public interface IdiomaCandidatoRepository extends JpaRepository<IdiomaCandidato, IdiomaCandidatoId> {

    Optional<IdiomaCandidato> findByCandidatoAndIdioma(Candidato candidato, Idioma idioma);
}
