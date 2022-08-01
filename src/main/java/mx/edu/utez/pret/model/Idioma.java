package mx.edu.utez.pret.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "idiomas")
@NoArgsConstructor
@AllArgsConstructor
public class Idioma extends Catalogo {
    @OneToMany(mappedBy = "idioma")
    private Set<IdiomaCandidato> candidatos;

    @Builder(buildMethodName = "idiomaBuilder")
    public Idioma(Long id, String nombre, Set<IdiomaCandidato> candidatos) {
        super(id, nombre);
        this.candidatos = candidatos;
    }
}
