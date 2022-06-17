package mx.edu.utez.pret.pojo;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class EstadoVacantePojo extends CatalogoPojo {
    private List<CandidatoVacantePojo> candidatoVacantes;

    @Builder(buildMethodName = "estadoVacanteBuilder")
    public EstadoVacantePojo(Long id, String nombre, List<CandidatoVacantePojo> candidatoVacantes) {
        super(id, nombre);
        this.candidatoVacantes = candidatoVacantes;
    }
}
