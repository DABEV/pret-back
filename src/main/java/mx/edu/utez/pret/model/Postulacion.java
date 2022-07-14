package mx.edu.utez.pret.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "postulaciones")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Postulacion {
    @EmbeddedId
    private PostulacionId id;

    @ManyToOne
    @MapsId("candidatoId")
    @JoinColumn(nullable = false, name = "candidato_id")
    private Candidato candidato;

    @ManyToOne
    @MapsId("vacanteId")
    @JoinColumn(nullable = false, name = "vacante_id")
    private Vacante vacante;

    @Column(nullable = true)
    private String cv;

    @ManyToOne
    @JoinColumn(nullable = false, name = "estado_vacante_id")
    private EstadoVacante estadoVacante;
}
