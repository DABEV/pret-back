package mx.edu.utez.pret.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class AuthResponsePojo {
    private String email;
    private String accessToken;
    private Set<RolPojo> roles;
}
