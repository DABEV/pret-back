package mx.edu.utez.pret.pojo;

import java.util.Date;
import java.util.List;
import java.util.Set;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ReclutadorPojo extends UsuarioPojo {
    private PuestoPojo puesto;
    private String nombreEmpresa;
    private EstadoRepublicaPojo estadoRepublicaEmpresa;
    private List<VacantePojo> vacantes;

    @Builder(buildMethodName = "reclutadorBuilder")
    public ReclutadorPojo(Long id, String nombre, String apellidoPaterno, String apellidoMaterno, String correoElectronico,
            String contrasena, Boolean habilitado, String telefono, Date fechaNacimiento, EstadoRepublicaPojo estadoRepublica, Set<RolPojo> roles,
            PuestoPojo puesto, String nombreEmpresa, EstadoRepublicaPojo estadoRepublicaEmpresa, List<VacantePojo> vacantes) {
        super(id, nombre, apellidoPaterno, apellidoMaterno, correoElectronico, contrasena, habilitado, telefono, fechaNacimiento,
                estadoRepublica, roles);
        this.puesto = puesto;
        this.nombreEmpresa = nombreEmpresa;
        this.estadoRepublicaEmpresa = estadoRepublicaEmpresa;
        this.vacantes = vacantes;
    }
}
