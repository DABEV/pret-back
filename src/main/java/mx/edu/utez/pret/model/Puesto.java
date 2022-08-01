package mx.edu.utez.pret.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "puestos")
public class Puesto extends Catalogo {
    @OneToMany(mappedBy = "puesto")
    private List<Reclutador> reclutadores;
    
    @Builder(buildMethodName = "puestoBuilder")
    public Puesto(Long id, String nombre, List<Reclutador> reclutadores) {
        super(id, nombre);
        this.reclutadores = reclutadores;
    }
}
