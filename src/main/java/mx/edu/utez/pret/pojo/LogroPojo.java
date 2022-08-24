package mx.edu.utez.pret.pojo;

import java.time.LocalDate;

import lombok.*;
import mx.edu.utez.pret.validator.ParagraphFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class LogroPojo {
    private Long id;

    @NotBlank
    @ParagraphFormat
    private String nombre;

    @NotBlank
    @ParagraphFormat
    private String empresa;

    @NotNull
    private LocalDate fechaObtencion;

    private CandidatoPojo candidato;
}
