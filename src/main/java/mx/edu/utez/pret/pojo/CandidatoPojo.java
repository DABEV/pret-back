package mx.edu.utez.pret.pojo;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.pret.validator.JobTitleFormat;
import mx.edu.utez.pret.validator.ParagraphFormat;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CandidatoPojo extends UsuarioPojo {
    @JobTitleFormat
    private String tituloCurricular;
    
    @ParagraphFormat
    private String descripcionPerfil;
    
    private String foto;
    
    private ConocimientoHabilidadPojo conocimientosHabilidades;
    private List<CursoPojo> cursos;
    private List<CertificacionPojo> certificaciones;
    private List<ExperienciaLaboralPojo> experienciasLaborales;
    private List<EstudioPojo> estudios;
    private Set<ContactoPojo> contactos;
    private Set<PostulacionPojo> postulaciones;
    private Set<IdiomaCandidatoPojo> idiomas;
    private Set<VacantePojo> vacantesFavoritas;
    
    @Builder(buildMethodName = "candidatoBuilder")
    public CandidatoPojo(
        long id, String nombre, String apellidoPaterno, String apellidoMaterno, String correoElectronico,
        String contrasena, Boolean habilitado, String telefono, LocalDate fechaNacimiento, EstadoRepublicaPojo estadoRepublica, Set<RolPojo> roles,
        String tituloCurricular, String descripcionPerfil, String foto,
        ConocimientoHabilidadPojo conocimientosHabilidades, List<CursoPojo> cursos, List<CertificacionPojo> certificaciones,
        List<ExperienciaLaboralPojo> experienciasLaborales, List<EstudioPojo> estudios, Set<ContactoPojo> contactos,
        Set<PostulacionPojo> postulaciones, Set<IdiomaCandidatoPojo> idiomas, Set<VacantePojo> vacantesFavoritas) {
        super(id, nombre, apellidoPaterno, apellidoMaterno, correoElectronico, contrasena, habilitado, telefono, fechaNacimiento,
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
