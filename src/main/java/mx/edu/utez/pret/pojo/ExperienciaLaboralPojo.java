package mx.edu.utez.pret.pojo;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExperienciaLaboralPojo {
    private Long id;
    @NotNull
    private CandidatoPojo candidato;
    @NotBlank
    @Size(max = 100)
    private String puesto;
    @NotNull
    private LocalDate fechaInicio;
    
    private LocalDate fechaFin;
    @NotBlank
    private String actividadesRealizadas;
}
