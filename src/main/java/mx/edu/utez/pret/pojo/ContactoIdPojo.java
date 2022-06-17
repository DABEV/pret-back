package mx.edu.utez.pret.pojo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ContactoIdPojo implements Serializable {
    private Long candidatoId;
    private Long amigoId;
}
