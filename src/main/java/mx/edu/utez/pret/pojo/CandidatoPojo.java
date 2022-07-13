package mx.edu.utez.pret.pojo;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CandidatoPojo extends UsuarioPojo {
    private String tituloCurricular;
    private String descripcionPerfil;
    private String foto;
    private Map<String, Object> conocimientosHabilidades;
    private List<CursoPojo> cursos;
    private List<CertificacionPojo> certificaciones;
    private List<ExperienciaLaboralPojo> experienciasLaborales;
    private List<EstudioPojo> estudios;
    private Set<ContactoPojo> contactos;
    private Set<PostulacionPojo> postulaciones;
    private Set<IdiomaCandidatoPojo> idiomas;
    private Set<VacantePojo> vacantesFavoritas;
    
    @Builder(buildMethodName = "candidatoBuilder")
    public CandidatoPojo(Long id, String nombre, String apellidoPaterno, String apellidoMaterno, String correoElectronico,
            String contrasena, String telefono, Date fechaNacimiento, EstadoRepublicaPojo estadoRepublica, Set<RolPojo> roles,
            String tituloCurricular, String descripcionPerfil, String foto,
            Map<String, Object> conocimientosHabilidades, List<CursoPojo> cursos, List<CertificacionPojo> certificaciones,
            List<ExperienciaLaboralPojo> experienciasLaborales, List<EstudioPojo> estudios, Set<ContactoPojo> contactos,
            Set<PostulacionPojo> postulaciones, Set<IdiomaCandidatoPojo> idiomas, Set<VacantePojo> vacantesFavoritas) {
        super(id, nombre, apellidoPaterno, apellidoMaterno, correoElectronico, contrasena, telefono, fechaNacimiento,
                estadoRepublica, roles);
        this.tituloCurricular = tituloCurricular;
        this.descripcionPerfil = descripcionPerfil;
        this.foto = foto;
        this.conocimientosHabilidades = conocimientosHabilidades;
        this.cursos = cursos;
        this.certificaciones = certificaciones;
        this.experienciasLaborales = experienciasLaborales;
        this.estudios = estudios;
        this.contactos = contactos;
        this.postulaciones = postulaciones;
        this.idiomas = idiomas;
        this.vacantesFavoritas = vacantesFavoritas;
    }
}
