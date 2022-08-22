package mx.edu.utez.pret.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.pret.pojo.ConocimientoHabilidadPojo;

@Entity
@Table(name = "candidatos")
@PrimaryKeyJoinColumn(name = "id")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@TypeDefs({
    @TypeDef(name = "json", typeClass = JsonStringType.class),
    @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
public class Candidato extends Usuario {
    @Column(name = "titulo_curricular")
    private String tituloCurricular;

    @Column(name = "descripcion_perfil", columnDefinition = "text null")
    private String descripcionPerfil;

    @Column
    private String foto;

    @Column(name = "conocimientos_habilidades", columnDefinition = "json")
    @Type(type = "json")
    private ConocimientoHabilidadPojo conocimientosHabilidades;

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
        String contrasena, Boolean habilitado, String telefono, LocalDate fechaNacimiento, EstadoRepublica estadoRepublica, Set<Rol> roles,
        String tituloCurricular, String descripcionPerfil, String foto,
        ConocimientoHabilidadPojo conocimientosHabilidades, List<Curso> cursos, List<Certificacion> certificaciones,
        List<ExperienciaLaboral> experienciasLaborales, List<Estudio> estudios, Set<Contacto> contactos,
        Set<Postulacion> postulaciones, Set<IdiomaCandidato> idiomas, Set<Vacante> vacantesFavoritas) {
        super(id, nombre, apellidoPaterno, apellidoMaterno, correoElectronico, contrasena, habilitado, telefono, fechaNacimiento, estadoRepublica, roles);
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
