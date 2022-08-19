package mx.edu.utez.pret.pojo;

import java.util.Date;

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
    private CandidatoPojo candidato;
    private UniversidadPojo universidad;
    private String carrera;
    private String gradoAcademico;
    private Date fechaInicio;
    private Date fechaFin;
}
