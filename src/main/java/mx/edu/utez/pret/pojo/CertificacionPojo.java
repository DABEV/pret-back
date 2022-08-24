package mx.edu.utez.pret.pojo;

import java.time.LocalDate;

import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class CertificacionPojo extends LogroPojo {
    private LocalDate fechaCaducidad;
    
    @Builder(buildMethodName = "certificacionBuilder")
    public CertificacionPojo(Long id, String nombre, String empresa, LocalDate fechaObtencion, CandidatoPojo candidato,
            LocalDate fechaCaducidad) {
        super(id, nombre, empresa, fechaObtencion, candidato);
        this.fechaCaducidad = fechaCaducidad;
    }
}
