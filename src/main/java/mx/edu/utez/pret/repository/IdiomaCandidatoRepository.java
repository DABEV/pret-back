package mx.edu.utez.pret.repository;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mx.edu.utez.pret.model.IdiomaCandidato;
import mx.edu.utez.pret.model.IdiomaCandidatoId;

public interface IdiomaCandidatoRepository extends JpaRepository<IdiomaCandidato, IdiomaCandidatoId> {

    @Query(value = "SELECT * FROM idioma_candidato WHERE candidato_id = :candidado AND idioma_id = :idioma", nativeQuery = true)
    Optional<IdiomaCandidato> selectByCandidatoIdAndIdiomaId(@Param("candidado") long candidato, @Param("idioma") long idioma);
}
