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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "experiencias_laborales")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ExperienciaLaboral {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false, name = "candidato_id")
    private Candidato candidato;

    @Column(nullable = false, length = 100)
    private String puesto;

    @Column(nullable = false, name = "fecha_inicio")
    private Date fechaInicio;

    @Column(nullable = true, name = "fecha_fin")
    private Date fechaFin;

    @Column(nullable = false, name = "actividades_realizadas")
    private String actividadesRealizadas;
}
