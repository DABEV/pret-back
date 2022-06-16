package mx.edu.utez.pret.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "idioma_candidato", indexes = @Index(columnList = "nivel"))
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class IdiomaCandidato {
    @EmbeddedId
    private IdiomaCandidatoId id;

    @Column(nullable = false, length = 15)
    private String nivel;

    @ManyToOne
    @MapsId("candidatoId")
    @JoinColumn(nullable = false, name = "candidato_id")
    private Candidato candidato;

    @ManyToOne
    @MapsId("idiomaId")
    @JoinColumn(nullable = false, name = "idioma_id")
    private Idioma idioma;
}
