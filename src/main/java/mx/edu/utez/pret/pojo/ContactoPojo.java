package mx.edu.utez.pret.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ContactoPojo {
    private ContactoIdPojo id;
    private Boolean estado;
    private CandidatoPojo candidato;
    private CandidatoPojo amigo;
}

