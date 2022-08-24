package mx.edu.utez.pret.pojo;

import java.io.Serializable;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactoIdPojo implements Serializable {
    private Long candidatoId;
    private Long amigoId;
}
