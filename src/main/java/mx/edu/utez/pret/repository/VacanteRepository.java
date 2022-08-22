package mx.edu.utez.pret.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.edu.utez.pret.model.Reclutador;
import mx.edu.utez.pret.model.Vacante;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VacanteRepository extends JpaRepository<Vacante, Long> {
    @Query("SELECT v FROM Vacante v WHERE v.fechaVigencia > :actualDate ORDER BY v.fechaVigencia ASC")
    List<Vacante> findAllVacantesWithActualDate(@Param("actualDate") LocalDate actualDate);

    List<Vacante> findByReclutador(Reclutador reclutador);
}
