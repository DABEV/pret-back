package mx.edu.utez.pret.pojo;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequestPojo {
    @NotBlank 
    @Email 
    private String correoElectronico;
     
    @NotBlank
    private String contrasena;
}
