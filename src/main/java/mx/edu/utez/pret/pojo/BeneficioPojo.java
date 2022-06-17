package mx.edu.utez.pret.pojo;

import java.util.Set;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BeneficioPojo extends CatalogoPojo {
    private Set<VacantePojo> vacantes;

    @Builder(buildMethodName = "beneficioBuilder")
    public BeneficioPojo(Long id, String nombre, Set<VacantePojo> vacantes) {
        super(id, nombre);
        this.vacantes = vacantes;
    }
}
