package mx.edu.utez.pret.pojo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EstadoRepublicaPojo extends CatalogoPojo {
    private List<CandidatoPojo> candidatos;
    private List<UniversidadPojo> universidades;
    private List<ReclutadorPojo> reclutadores;

    @Builder(buildMethodName = "estadoRepublicaBuilder")
    public EstadoRepublicaPojo(Long id, String nombre, List<CandidatoPojo> candidatos, List<UniversidadPojo> universidades,
            List<ReclutadorPojo> reclutadores) {
        super(id, nombre);
        this.candidatos = candidatos;
        this.universidades = universidades;
        this.reclutadores = reclutadores;
    }
}
