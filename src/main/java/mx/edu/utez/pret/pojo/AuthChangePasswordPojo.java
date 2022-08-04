package mx.edu.utez.pret.pojo;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthChangePasswordPojo {
    @NotBlank 
    private String contrasena;
     
    @NotBlank
    private String nuevaContrasena;
     
    @NotBlank
    private String repetirContrasena;
}
