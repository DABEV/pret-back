package mx.edu.utez.pret.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "estudios")
public class Estudio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false, name = "candidato_id")
    private Candidato candidato;

    @ManyToOne
    @JoinColumn(nullable = false, name = "universidad_id")
    private Universidad universidad;

    @Column(nullable = false, length = 100)
    private String carrera;

    @Column(nullable = false, name = "grado_academico", length = 30)
    private String gradoAcademico;

    @Column(nullable = false, name = "fecha_inicio")
    private Date fechaInicio;

    @Column(name = "fecha_fin", nullable = true)
    private Date fechaFin;
}
