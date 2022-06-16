package mx.edu.utez.pret.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "vacantes", indexes = {@Index(columnList = "modalidad"), @Index(columnList = "tipo"), @Index(columnList = "periodo_pago")})
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Vacante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = true)
    private String descripcion;
    
    @Column(nullable = false)
    private String modalidad;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false, name = "fecha_inicio")
    private Date fechaInicio;

    @Column(nullable = false, name = "sueldo_min")
    private Integer sueldoMin;

    @Column(nullable = false, name = "sueldo_max")
    private Integer sueldoMax;
    
    @Column(nullable = false, name = "periodo_pago")
    private String periodoPago;

    @Column(nullable = false, name = "fecha_vigencia")
    private Date fechaVigencia;

    @OneToMany(mappedBy = "vacante")
    private Set<CandidatoVacante> candidatosVacantes;

    @ManyToOne
    @JoinColumn(nullable = false, name = "reclutador_id")
    private Reclutador reclutador;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "vacante_beneficio", joinColumns = @JoinColumn(nullable = false, name = "vacante_id"), inverseJoinColumns = @JoinColumn(nullable = false, name = "beneficio_id"))
    private Set<Beneficio> beneficios;

    @ManyToMany(mappedBy = "vacantesFavoritas")
    private Set<Candidato> candidatosEnFavoritos;
}
