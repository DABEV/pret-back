package mx.edu.utez.pret.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IdiomaCandidatoPojo {
    private IdiomaCandidatoIdPojo id;
    private String nivel;
    private CandidatoPojo candidato;
    private IdiomaPojo idioma;
}
