package mx.edu.utez.pret.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponsePojo {
    private String email;
    private String accessToken;
}
