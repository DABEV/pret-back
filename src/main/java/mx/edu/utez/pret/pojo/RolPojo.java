package mx.edu.utez.pret.pojo;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RolPojo extends CatalogoPojo{
    private Set<UsuarioPojo> usuarios;

    @Builder(buildMethodName = "rolBuilder")
    public RolPojo(Long id, String nombre, Set<UsuarioPojo> usuarios) {
        super(id, nombre);
        this.usuarios = usuarios;
    }
}
