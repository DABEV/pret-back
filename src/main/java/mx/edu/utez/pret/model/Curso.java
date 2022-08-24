package mx.edu.utez.pret.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cursos")
@Getter @Setter
public class Curso extends Logro {
    @Column(nullable = false, name = "numero_horas")
    private Integer numeroHoras;

    @Builder(buildMethodName = "cursoBuilder")
    public Curso(Long id, String nombre, String empresa, LocalDate fechaObtencion, Candidato candidato,
                 Integer numeroHoras) {
        super(id, nombre, empresa, fechaObtencion, candidato);
        this.numeroHoras = numeroHoras;
    }
}
