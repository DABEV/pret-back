package mx.edu.utez.pret.repository;

import mx.edu.utez.pret.model.Candidato;
import org.springframework.data.jpa.repository.JpaRepository;

import mx.edu.utez.pret.model.Certificacion;

import java.util.List;

public interface CertificacionRepository extends JpaRepository<Certificacion, Long> {
    List<Certificacion> findByCandidato(Candidato candidato);
}
