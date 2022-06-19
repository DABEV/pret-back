package mx.edu.utez.pret.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.edu.utez.pret.model.Contacto;
import mx.edu.utez.pret.model.ContactoId;

public interface ContactoRepository extends JpaRepository<Contacto, ContactoId> {
}
