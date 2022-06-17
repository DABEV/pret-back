package mx.edu.utez.pret.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CandidatoVacantePojo {
    private CandidatoVacanteIdPojo id;
    private CandidatoPojo candidato;
    private VacantePojo vacante;
    private String cv;
    private EstadoVacantePojo estadoVacante;
}
