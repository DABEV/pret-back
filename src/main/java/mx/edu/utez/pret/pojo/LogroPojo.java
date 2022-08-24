package mx.edu.utez.pret.pojo;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LogroPojo {
    private Long id;
    private String nombre;
    private String empresa;
    private LocalDate fechaObtencion;
    private CandidatoPojo candidato;
}
