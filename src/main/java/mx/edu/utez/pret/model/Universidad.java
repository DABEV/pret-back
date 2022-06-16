package mx.edu.utez.pret.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "universidades")
@Getter @Setter
public class Universidad extends Catalogo {
    @Column(nullable = true)
    private String siglas;

    @OneToMany(mappedBy = "universidad")
    private List<Estudio> estudios;

    @ManyToOne
    @JoinColumn(nullable = false, name = "estado_republica_id")
    private EstadoRepublica estadoRepublica;

    @Builder(builderMethodName = "universidadBuilder")
    public Universidad(Long id, String nombre, String siglas, List<Estudio> estudios, EstadoRepublica estadoRepublica) {
        super(id, nombre);
        this.siglas = siglas;
        this.estudios = estudios;
        this.estadoRepublica = estadoRepublica;
    }
}
