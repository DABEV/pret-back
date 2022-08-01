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
public class UniversidadPojo extends CatalogoPojo {
    private String siglas;
    private List<EstudioPojo> estudios;
    private EstadoRepublicaPojo estadoRepublica;

    @Builder(builderMethodName = "universidadBuilder")
    public UniversidadPojo(Long id, String nombre, String siglas, List<EstudioPojo> estudios, EstadoRepublicaPojo estadoRepublica) {
        super(id, nombre);
        this.siglas = siglas;
        this.estudios = estudios;
        this.estadoRepublica = estadoRepublica;
    }
}
