package mx.edu.utez.pret.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.edu.utez.pret.model.Certificacion;

public interface CertificacionRepository extends JpaRepository<Certificacion, Long> {
}
