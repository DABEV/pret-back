package mx.edu.utez.pret.pojo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EstudioPojo {
    private Long id;
    private CandidatoPojo candidato;
    private UniversidadPojo universidad;
    private String carrera;
    private String gradoAcademico;
    private Date fechaInicio;
    private Date fechaFin;
}
