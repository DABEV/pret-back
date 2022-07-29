package mx.edu.utez.pret.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usuarios")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, name = "apellido_paterno", length = 50)
    private String apellidoPaterno;

    @Column(nullable = true, name = "apellido_materno", length = 50)
    private String apellidoMaterno;

    @Column(nullable = false, name = "correo", unique = true)
    private String correoElectronico;

    @Column(nullable = false)
    private String contrasena;

    @Column(nullable = false, columnDefinition="tinyint default 1")
    private Boolean habilitado;

    @Column(nullable = true, length = 20)
    private String telefono;

    @Column(nullable = false, name = "fecha_nacimiento")
    private Date fechaNacimiento;

    @ManyToOne
    @JoinColumn(nullable = false, name = "estado_republica_id")
    private EstadoRepublica estadoRepublica;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "usuario_rol", joinColumns = @JoinColumn(nullable = false, name = "usuario_id"), inverseJoinColumns = @JoinColumn(nullable = false, name = "rol_id"))
    private Set<Rol> roles;

    public void addRol(Rol rol) {
        if (this.roles == null)
            this.roles = new HashSet<>();
        
        roles.add(rol);
    }
}
