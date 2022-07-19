package mx.edu.utez.pret.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Rol extends Catalogo{
    @ManyToMany(mappedBy = "roles")
    private Set<Usuario> usuarios;

    @Builder(buildMethodName = "rolBuilder")
    public Rol(Long id, String nombre, Set<Usuario> usuarios) {
        super(id, nombre);
        this.usuarios = usuarios;
    }
}
