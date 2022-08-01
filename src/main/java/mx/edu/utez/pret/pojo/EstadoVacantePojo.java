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
public class EstadoVacantePojo extends CatalogoPojo {
    private List<PostulacionPojo> postulaciones;

    @Builder(buildMethodName = "estadoVacanteBuilder")
    public EstadoVacantePojo(Long id, String nombre, List<PostulacionPojo> postulaciones) {
        super(id, nombre);
        this.postulaciones = postulaciones;
    }
}
