package mx.edu.utez.pret.pojo;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExperienciaLaboralPojo {
    private Long id;
    private CandidatoPojo candidato;
    private String puesto;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String actividadesRealizadas;
}
