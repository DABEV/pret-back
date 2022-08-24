package mx.edu.utez.pret.repository;

import mx.edu.utez.pret.model.Candidato;
import org.springframework.data.jpa.repository.JpaRepository;

import mx.edu.utez.pret.model.Contacto;
import mx.edu.utez.pret.model.ContactoId;

import java.util.List;

public interface ContactoRepository extends JpaRepository<Contacto, ContactoId> {
    List<Contacto> findByCandidato(Candidato candidato);
    List<Contacto> findByAmigo(Candidato candidato);
}
