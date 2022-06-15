package mx.edu.utez.pret.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter @Setter
@EqualsAndHashCode
@NoArgsConstructor @AllArgsConstructor
public class ContactoId implements Serializable {
    @Column(nullable = false, name = "candidato_id")
    private Long candidatoId;

    @Column(nullable = false, name = "amigo_id")
    private Long amigoId;
}
