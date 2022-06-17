package mx.edu.utez.pret.pojo;

import java.util.Set;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class IdiomaPojo extends CatalogoPojo {
    private Set<IdiomaCandidatoPojo> candidatos;

    @Builder(buildMethodName = "idiomaBuilder")
    public IdiomaPojo(Long id, String nombre, Set<IdiomaCandidatoPojo> candidatos) {
        super(id, nombre);
        this.candidatos = candidatos;
    }
}
