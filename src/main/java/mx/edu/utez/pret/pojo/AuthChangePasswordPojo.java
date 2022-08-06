package mx.edu.utez.pret.pojo;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import mx.edu.utez.pret.validator.PasswordFormat;

@Data
@AllArgsConstructor
public class AuthChangePasswordPojo {
    @NotBlank 
    private String contrasena;
     
    @NotBlank
    @PasswordFormat
    private String nuevaContrasena;
     
    @NotBlank
    @PasswordFormat
    private String repetirContrasena;
}
