package mx.edu.utez.pret.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "beneficios")
@NoArgsConstructor
@AllArgsConstructor
public class Beneficio extends Catalogo {
    @ManyToMany(mappedBy = "beneficios")
    private Set<Vacante> vacantes;

    @Builder(buildMethodName = "beneficioBuilder")
    public Beneficio(Long id, String nombre, Set<Vacante> vacantes) {
        super(id, nombre);
        this.vacantes = vacantes;
    }
}
