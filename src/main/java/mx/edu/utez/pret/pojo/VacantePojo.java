package mx.edu.utez.pret.pojo;

import java.time.LocalDate;
import java.util.Set;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.pret.validator.NameFormat;
import mx.edu.utez.pret.validator.ParagraphFormat;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class VacantePojo {
    private Long id;
    
    @ParagraphFormat
    @NotBlank    
    private String nombre;

    @ParagraphFormat
    private String descripcion;

    @NameFormat
    @NotBlank
    private String modalidad;

    @NameFormat
    @NotBlank
    private String tipo;

    @NotNull
    private LocalDate fechaInicio;

    @NotNull
    @Min(150) @Max(1000000)
    private Integer sueldoMin;

    @NotNull
    @Min(150) @Max(1000000)
    private Integer sueldoMax;

    @NameFormat
    @NotBlank
    private String periodoPago;

    @NotNull
    private LocalDate fechaVigencia;

    private Set<PostulacionPojo> postulaciones;
    private ReclutadorPojo reclutador;
    private Set<BeneficioPojo> beneficios;
    private Set<CandidatoPojo> candidatosEnFavoritos;
}
