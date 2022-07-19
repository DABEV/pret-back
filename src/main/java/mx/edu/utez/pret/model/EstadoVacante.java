package mx.edu.utez.pret.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "estados_vacante")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class EstadoVacante extends Catalogo {
    @OneToMany(mappedBy = "estadoVacante")
    private List<Postulacion> postulaciones;

    @Builder(buildMethodName = "estadoVacanteBuilder")
    public EstadoVacante(Long id, String nombre, List<Postulacion> postulaciones) {
        super(id, nombre);
        this.postulaciones = postulaciones;
    }
}
