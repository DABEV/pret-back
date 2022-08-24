package mx.edu.utez.pret.pojo;

import java.time.LocalDate;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class CursoPojo extends LogroPojo {

    @NotNull
    @Min(1) @Max(1000)
    private Integer numeroHoras;

    @Builder(buildMethodName = "cursoBuilder")
    public CursoPojo(Long id, String nombre, String empresa, LocalDate fechaObtencion, CandidatoPojo candidato,
                     Integer numeroHoras) {
        super(id, nombre, empresa, fechaObtencion, candidato);
        this.numeroHoras = numeroHoras;
    }
}
