package mx.edu.utez.pret.pojo;

import java.util.Date;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.utez.pret.validator.NameFormat;
import mx.edu.utez.pret.validator.PhoneFormat;

@Data
@AllArgsConstructor @NoArgsConstructor
public class UsuarioPojo {

    private Long id;
    
    @NameFormat
    @Size(max = 50)
    @NotBlank
    private String nombre;
    
    @NameFormat
    @Size(max = 50)
    @NotBlank
    private String apellidoPaterno;
    
    @NameFormat
    @Size(max = 50)
    private String apellidoMaterno;
    
    @NotBlank
    @Email
    private String correoElectronico;
    
    @NotBlank
    private String contrasena;
    
    @NotNull
    private Boolean habilitado;
    
    @PhoneFormat
    @NotBlank
    @Size(max = 50)
    private String telefono;
    
    @NotNull
    private Date fechaNacimiento;
    
    @NotNull
    private EstadoRepublicaPojo estadoRepublica;

    private Set<RolPojo> roles;
}
