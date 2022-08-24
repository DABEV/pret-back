package mx.edu.utez.pret.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "certificaciones")
@Getter @Setter
public class Certificacion extends Logro {
    @Column(nullable = false, name = "fecha_caducidad")
    private LocalDate fechaCaducidad;
    
    @Builder(buildMethodName = "certificacionBuilder")
    public Certificacion(Long id, String nombre, String empresa, LocalDate fechaObtencion, Candidato candidato,
                         LocalDate fechaCaducidad) {
        super(id, nombre, empresa, fechaObtencion, candidato);
        this.fechaCaducidad = fechaCaducidad;
    }
}
