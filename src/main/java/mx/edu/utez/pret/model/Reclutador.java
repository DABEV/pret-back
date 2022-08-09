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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reclutadores")
@PrimaryKeyJoinColumn(name = "id")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Reclutador extends Usuario {
    @ManyToOne
    @JoinColumn(nullable = false, name = "puesto_id")
    private Puesto puesto;

    @Column(nullable = false, name = "nombre_empresa", length = 100)
    private String nombreEmpresa;

    @Column(nullable = true)
    private String foto;
    
    @ManyToOne
    @JoinColumn(nullable = false, name = "estado_republica_empresa_id")
    private EstadoRepublica estadoRepublicaEmpresa;

    @OneToMany(mappedBy = "reclutador")
    private List<Vacante> vacantes;

    @Builder(buildMethodName = "reclutadorBuilder")
    public Reclutador(Long id, String nombre, String apellidoPaterno, String apellidoMaterno, String correoElectronico,
            String contrasena, Boolean habilitado, String telefono, Date fechaNacimiento, EstadoRepublica estadoRepublica, Set<Rol> roles,
            Puesto puesto, String nombreEmpresa, String foto, EstadoRepublica estadoRepublicaEmpresa, List<Vacante> vacantes) {
        super(id, nombre, apellidoPaterno, apellidoMaterno, correoElectronico, contrasena, habilitado ,telefono, fechaNacimiento,
                estadoRepublica, roles);
        this.puesto = puesto;
        this.nombreEmpresa = nombreEmpresa;
        this.foto = foto;
        this.estadoRepublicaEmpresa = estadoRepublicaEmpresa;
        this.vacantes = vacantes;
    }
}
