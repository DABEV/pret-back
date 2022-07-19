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
@Table(name = "estados_republica")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class EstadoRepublica extends Catalogo {
    @OneToMany(mappedBy = "estadoRepublica")
    private List<Candidato> candidatos;

    @OneToMany(mappedBy = "estadoRepublica")
    private List<Universidad> universidades;

    @OneToMany(mappedBy = "estadoRepublicaEmpresa")
    private List<Reclutador> reclutadores;

    @Builder(buildMethodName = "estadoRepublicaBuilder")
    public EstadoRepublica(Long id, String nombre, List<Candidato> candidatos, List<Universidad> universidades,
            List<Reclutador> reclutadores) {
        super(id, nombre);
        this.candidatos = candidatos;
        this.universidades = universidades;
        this.reclutadores = reclutadores;
    }
}
