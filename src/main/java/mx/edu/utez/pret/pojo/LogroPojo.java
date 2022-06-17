package mx.edu.utez.pret.pojo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LogroPojo {
    private Long id;
    private String nombre;
    private String empresa;
    private Date fechaObtencion;
    private CandidatoPojo candidato;
}
