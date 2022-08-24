package mx.edu.utez.pret.pojo;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CursoPojo extends LogroPojo {
    private Integer numeroHoras;

    @Builder(buildMethodName = "cursoBuilder")
    public CursoPojo(Long id, String nombre, String empresa, LocalDate fechaObtencion, CandidatoPojo candidato,
                     Integer numeroHoras) {
        super(id, nombre, empresa, fechaObtencion, candidato);
        this.numeroHoras = numeroHoras;
    }
}
