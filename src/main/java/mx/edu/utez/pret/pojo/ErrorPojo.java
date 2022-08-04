package mx.edu.utez.pret.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorPojo {
    private Integer code;
    private String error;
    private String description;
}
