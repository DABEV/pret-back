package mx.edu.utez.pret.pojo;

import java.util.Date;

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
    @NotBlank
    private Date fechaInicio;
    @NotBlank
    private Date fechaFin;
    @NotBlank
    private String actividadesRealizadas;
}
