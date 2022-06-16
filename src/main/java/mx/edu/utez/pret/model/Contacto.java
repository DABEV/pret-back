package mx.edu.utez.pret.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "contactos")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Contacto {
    @EmbeddedId
    private ContactoId id;

    @Column(nullable = false, columnDefinition = "tinyint default 0")
    private Boolean estado;

    @ManyToOne
    @MapsId("candidatoId")
    @JoinColumn(nullable = false, name = "candidato_id")
    private Candidato candidato;

    @ManyToOne
    @MapsId("amigoId")
    @JoinColumn(nullable = false, name = "amigo_id")
    private Candidato amigo;
}

