package mx.edu.utez.pret.pojo;

import java.util.Date;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsuarioPojo {
    private Long id;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String correoElectronico;
    private String contrasena;
    private String telefono;
    private Date fechaNacimiento;
    private EstadoRepublicaPojo estadoRepublica;
    private Set<RolPojo> roles;
}
