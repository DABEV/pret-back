package mx.edu.utez.pret.pojo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExperienciaLaboralPojo {
    private Long id;
    private CandidatoPojo candidato;
    private String puesto;
    private Date fechaInicio;
    private Date fechaFin;
    private String actividadesRealizadas;
}
