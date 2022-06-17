package mx.edu.utez.pret.pojo;

import java.util.Date;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CertificacionPojo extends LogroPojo {
    private Date fechaCaducidad;
    
    @Builder(buildMethodName = "certificacionBuilder")
    public CertificacionPojo(Long id, String nombre, String empresa, Date fechaObtencion, CandidatoPojo candidato,
            Date fechaCaducidad) {
        super(id, nombre, empresa, fechaObtencion, candidato);
        this.fechaCaducidad = fechaCaducidad;
    }
}
