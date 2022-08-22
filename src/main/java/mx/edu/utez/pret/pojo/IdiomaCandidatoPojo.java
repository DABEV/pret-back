package mx.edu.utez.pret.pojo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IdiomaCandidatoPojo {
    private IdiomaCandidatoIdPojo id;
    @NotBlank
    private String nivel;
    @NotNull
    private CandidatoPojo candidato;
    @NotNull
    private IdiomaPojo idioma;
}
