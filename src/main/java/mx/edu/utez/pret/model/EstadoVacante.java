package mx.edu.utez.pret.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "estados_vacante")
@Getter @Setter
public class EstadoVacante extends Catalogo {
    @OneToMany(mappedBy = "estadoVacante")
    private List<CandidatoVacante> candidatoVacantes;

    @Builder(buildMethodName = "estadoVacanteBuilder")
    public EstadoVacante(Long id, String nombre, List<CandidatoVacante> candidatoVacantes) {
        super(id, nombre);
        this.candidatoVacantes = candidatoVacantes;
    }
}
