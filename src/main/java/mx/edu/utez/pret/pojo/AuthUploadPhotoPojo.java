package mx.edu.utez.pret.pojo;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor @NoArgsConstructor
public class AuthUploadPhotoPojo {
    @NotBlank
    private String foto; 
}
