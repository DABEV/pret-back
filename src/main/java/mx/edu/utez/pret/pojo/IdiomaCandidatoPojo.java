package mx.edu.utez.pret.pojo;

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
    private String nivel;
    private CandidatoPojo candidato;
    private IdiomaPojo idioma;
}
