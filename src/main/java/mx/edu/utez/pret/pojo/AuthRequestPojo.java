package mx.edu.utez.pret.pojo;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthRequestPojo {
    @NotNull 
    @Email 
    private String correoElectronico;
     
    @NotNull
    private String contrasena;
}
