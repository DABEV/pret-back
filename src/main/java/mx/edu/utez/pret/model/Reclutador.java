package mx.edu.utez.pret.model;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "reclutadores")
@PrimaryKeyJoinColumn(name = "id")
@Getter @Setter
public class Reclutador extends Usuario {
    @ManyToOne
    @JoinColumn(nullable = false, name = "puesto_id")
    private Puesto puesto;

    @Column(nullable = false, name = "nombre_empresa", length = 100)
    private String nombreEmpresa;
    
    @ManyToOne
    @JoinColumn(nullable = false, name = "estado_republica_empresa_id")
    private EstadoRepublica estadoRepublicaEmpresa;

    @OneToMany(mappedBy = "reclutador")
    private List<Vacante> vacantes;

    @Builder(buildMethodName = "reclutadorBuilder")
    public Reclutador(Long id, String nombre, String apellidoPaterno, String apellidoMaterno, String correoElectronico,
            String contrasena, String telefono, Date fechaNacimiento, EstadoRepublica estadoRepublica, Set<Rol> roles,
            Puesto puesto, String nombreEmpresa, EstadoRepublica estadoRepublicaEmpresa, List<Vacante> vacantes) {
        super(id, nombre, apellidoPaterno, apellidoMaterno, correoElectronico, contrasena, telefono, fechaNacimiento,
                estadoRepublica, roles);
        this.puesto = puesto;
        this.nombreEmpresa = nombreEmpresa;
        this.estadoRepublicaEmpresa = estadoRepublicaEmpresa;
        this.vacantes = vacantes;
    }
}
