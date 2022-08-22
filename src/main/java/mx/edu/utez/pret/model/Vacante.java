package mx.edu.utez.pret.model;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.*;

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
    private LocalDate fechaInicio;

    @Column(nullable = false, name = "sueldo_min")
    private Integer sueldoMin;

    @Column(nullable = false, name = "sueldo_max")
    private Integer sueldoMax;
    
    @Column(nullable = false, name = "periodo_pago")
    private String periodoPago;

    @Column(nullable = false, name = "fecha_vigencia")
    private LocalDate fechaVigencia;

    @OneToMany(mappedBy = "vacante")
    private Set<Postulacion> postulaciones;

    @ManyToOne
    @JoinColumn(nullable = false, name = "reclutador_id")
    private Reclutador reclutador;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "vacante_beneficio", joinColumns = @JoinColumn(nullable = false, name = "vacante_id"), inverseJoinColumns = @JoinColumn(nullable = false, name = "beneficio_id"))
    private Set<Beneficio> beneficios;

    @ManyToMany(mappedBy = "vacantesFavoritas")
    private Set<Candidato> candidatosEnFavoritos;
}
