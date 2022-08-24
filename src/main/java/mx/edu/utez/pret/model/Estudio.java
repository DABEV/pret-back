package mx.edu.utez.pret.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "estudios")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin", nullable = true)
    private LocalDate fechaFin;
}
