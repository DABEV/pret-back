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
public class PuestoPojo extends CatalogoPojo {
    private List<ReclutadorPojo> reclutadores;
    
    @Builder(buildMethodName = "puestoBuilder")
    public PuestoPojo(Long id, String nombre, List<ReclutadorPojo> reclutadores) {
        super(id, nombre);
        this.reclutadores = reclutadores;
    }
}
