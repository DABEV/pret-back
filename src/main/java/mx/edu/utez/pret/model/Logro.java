package mx.edu.utez.pret.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Logro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String empresa;

    @Column(nullable = false, name = "fecha_obtencion")
    private Date fechaObtencion;

    @ManyToOne
    @JoinColumn(nullable = false, name = "candidato_id")
    private Candidato candidato;
}
