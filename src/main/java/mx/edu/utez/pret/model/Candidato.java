package mx.edu.utez.pret.model;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import mx.edu.utez.pret.config.JsonToMapConverter;

@Entity
@Table(name = "candidatos")
@PrimaryKeyJoinColumn(name = "id")
@Getter @Setter
public class Candidato extends Usuario {
    @Column(nullable = true, name = "titulo_curricular")
    private String tituloCurricular;

    @Column(nullable = false, name = "descripcion_perfil", columnDefinition = "text null")
    private String descripcionPerfil;

    @Column(nullable = false)
    private String foto;

    @Column(nullable = true, name = "conocimientos_habilidades", columnDefinition = "json")
    @Convert(converter = JsonToMapConverter.class)
    private Map<String, Object> conocimientosHabilidades;

    @OneToMany(mappedBy = "candidato")
    private List<Curso> cursos;

    @OneToMany(mappedBy = "candidato")
    private List<Certificacion> certificaciones;

    @OneToMany(mappedBy = "candidato")
    private List<ExperienciaLaboral> experienciasLaborales;

    @OneToMany(mappedBy = "candidato")
    private List<Estudio> estudios;

    @OneToMany(mappedBy = "candidato")
    private Set<Contacto> contactos;

    @OneToMany(mappedBy = "candidato")
    private Set<Postulacion> postulaciones;

    @OneToMany(mappedBy = "candidato")
    private Set<IdiomaCandidato> idiomas;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "favoritos", joinColumns = @JoinColumn(nullable = false, name = "candidato_id"), inverseJoinColumns = @JoinColumn(nullable = false, name = "vacante_id"))
    private Set<Vacante> vacantesFavoritas;
    
    @Builder(buildMethodName = "candidatoBuilder")
    public Candidato(Long id, String nombre, String apellidoPaterno, String apellidoMaterno, String correoElectronico,
            String contrasena,Boolean habilitado, String telefono, Date fechaNacimiento, EstadoRepublica estadoRepublica, Set<Rol> roles,
            String tituloCurricular, String descripcionPerfil, String foto,
            Map<String, Object> conocimientosHabilidades, List<Curso> cursos, List<Certificacion> certificaciones,
            List<ExperienciaLaboral> experienciasLaborales, List<Estudio> estudios, Set<Contacto> contactos,
            Set<Postulacion> postulaciones, Set<IdiomaCandidato> idiomas, Set<Vacante> vacantesFavoritas) {
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
