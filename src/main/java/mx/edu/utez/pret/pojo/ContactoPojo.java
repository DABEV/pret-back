package mx.edu.utez.pret.pojo;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactoPojo {
    private ContactoIdPojo id;
    private Boolean estado;
    private CandidatoPojo candidato;
    private CandidatoPojo amigo;
}

