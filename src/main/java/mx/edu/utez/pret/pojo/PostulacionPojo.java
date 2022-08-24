package mx.edu.utez.pret.pojo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostulacionPojo {
    private PostulacionIdPojo id;
    private CandidatoPojo candidato;
    private VacantePojo vacante;
    private String cv;
    private EstadoVacantePojo estadoVacante;
}
