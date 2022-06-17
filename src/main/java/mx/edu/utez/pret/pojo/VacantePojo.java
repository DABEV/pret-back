package mx.edu.utez.pret.pojo;

import java.util.Date;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VacantePojo {
    private Long id;
    private String nombre;
    private String descripcion;
    private String modalidad;
    private String tipo;
    private Date fechaInicio;
    private Integer sueldoMin;
    private Integer sueldoMax;
    private String periodoPago;
    private Date fechaVigencia;
    private Set<CandidatoVacantePojo> candidatosVacantes;
    private ReclutadorPojo reclutador;
    private Set<BeneficioPojo> beneficios;
    private Set<CandidatoPojo> candidatosEnFavoritos;
}
