package mx.edu.utez.pret.pojo;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EstudioPojo {
    private Long id;
    @NotNull
    private CandidatoPojo candidato;
    @NotNull
    private UniversidadPojo universidad;
    @NotBlank
    @Size(max = 100)
    private String carrera;
    @NotBlank
    private String gradoAcademico;
    @NotBlank
    private LocalDate fechaInicio;
    @NotBlank
    private LocalDate fechaFin;
}
