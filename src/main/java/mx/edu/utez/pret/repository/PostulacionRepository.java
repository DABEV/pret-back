package mx.edu.utez.pret.repository;

import mx.edu.utez.pret.model.Candidato;
import mx.edu.utez.pret.model.Postulacion;
import mx.edu.utez.pret.model.PostulacionId;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostulacionRepository extends JpaRepository<Postulacion, PostulacionId> {
    List<Postulacion> findByCandidato(Candidato candidato);
}
