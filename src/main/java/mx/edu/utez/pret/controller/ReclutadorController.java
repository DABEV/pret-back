package mx.edu.utez.pret.controller;

import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import mx.edu.utez.pret.config.CustomModelMapper;
import mx.edu.utez.pret.config.JwtTokenFilter;
import mx.edu.utez.pret.config.Response;
import mx.edu.utez.pret.model.EstadoRepublica;
import mx.edu.utez.pret.model.Puesto;
import mx.edu.utez.pret.model.Reclutador;
import mx.edu.utez.pret.model.Usuario;
import mx.edu.utez.pret.pojo.EstadoRepublicaPojo;
import mx.edu.utez.pret.pojo.PuestoPojo;
import mx.edu.utez.pret.pojo.ReclutadorPojo;
import mx.edu.utez.pret.service.ReclutadorServiceImp;
import mx.edu.utez.pret.service.RolServiceImp;

@RestController
@RequestMapping("/reclutador")
public class ReclutadorController {

    @Autowired
    private ReclutadorServiceImp serviceImp;

    @Autowired
    private RolServiceImp rolServiceImp;

    @Autowired
    private CustomModelMapper modelMapper;

    @Autowired
    private Response response;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    private static final String ROLE = "ROL_RECLUTADOR"; 

    // Response default data
    private String title;
    private String message;
    private HttpStatus status;

    /**
     * Return specific data to response
     */
    private ReclutadorPojo createPojoFromFormFields(Reclutador reclutador) {
        return ReclutadorPojo.builder()
            .nombre(reclutador.getNombre())
            .apellidoPaterno(reclutador.getApellidoPaterno())
            .apellidoMaterno(reclutador.getApellidoMaterno())
            .correoElectronico(reclutador.getCorreoElectronico())
            .telefono(reclutador.getTelefono())
            .fechaNacimiento(reclutador.getFechaNacimiento())
            .estadoRepublica(modelMapper.map(reclutador.getEstadoRepublica(), EstadoRepublicaPojo.class))
            .puesto(modelMapper.map(reclutador.getPuesto(), PuestoPojo.class))
            .nombreEmpresa(reclutador.getNombreEmpresa())
            .estadoRepublicaEmpresa(modelMapper.map(reclutador.getEstadoRepublicaEmpresa(), EstadoRepublicaPojo.class))
            .reclutadorBuilder();
    }

    @Transactional
    @PostMapping("/registrar")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> registrar (@Valid @RequestBody ReclutadorPojo reclutadorPojo) {
        title = "Registrar reclutador";
        message = "Error al guardar el reclutador";
        ReclutadorPojo answ = null;
        status = HttpStatus.OK;
        Reclutador reclutador = modelMapper.map(reclutadorPojo, Reclutador.class);
        
        reclutador.setHabilitado(true);
        // Encrypt password
        reclutador.setContrasena(passwordEncoder.encode(reclutador.getContrasena()));
        // Set Reclutador Role
        reclutador.addRol(rolServiceImp.buscarPorNombre(ROLE));
        reclutador = serviceImp.guardar(reclutador);

        if (reclutador != null && reclutador.getId() != null) {
            answ = createPojoFromFormFields(reclutador);
            title = "Reclutador registrado";
            message = "El reclutador ha sido registrado satisfactoriamente";
            status = HttpStatus.CREATED;
        }

        return new ResponseEntity<>(response.buildStandardResponse(title, answ, message), status);
    }

    @Transactional
    @PostMapping("/actualizar")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> actualizar (@RequestHeader HttpHeaders headers, @Valid @RequestBody ReclutadorPojo reclutadorPojo) {
        title = "Actualizar Reclutador";
        message = "No se realizo la actualización";
        ReclutadorPojo answ = null;
        Usuario usuario = jwtTokenFilter.getUserDetails(headers);
        Optional<Reclutador> reclutadorDb = serviceImp.obtenerPorId(usuario.getId());
        
        if (reclutadorDb.isPresent() && usuario.getId() != null) {
            Reclutador reclutador = reclutadorDb.get();

            reclutador.setNombre(reclutadorPojo.getNombre());
            reclutador.setApellidoPaterno(reclutadorPojo.getApellidoPaterno());
            reclutador.setApellidoMaterno(reclutadorPojo.getApellidoMaterno());
            reclutador.setTelefono(reclutadorPojo.getTelefono());
            reclutador.setFechaNacimiento(reclutadorPojo.getFechaNacimiento());
            reclutador.setPuesto(modelMapper.map(reclutadorPojo.getPuesto(), Puesto.class));
            reclutador.setNombreEmpresa(reclutadorPojo.getNombreEmpresa());
            reclutador.setEstadoRepublicaEmpresa(modelMapper.map(reclutadorPojo.getEstadoRepublicaEmpresa(), EstadoRepublica.class));

            reclutador = serviceImp.guardar(reclutador);

            title = "Reclutador actualizado";
            message = "Actualización exitosa";
            answ = createPojoFromFormFields(reclutador);
        }

        return new ResponseEntity<>(response.buildStandardResponse(title, answ, message), HttpStatus.OK);
    }

    @GetMapping(value = {"/perfil", "/perfil/{id}"})
    @ResponseBody
    public ResponseEntity<Map<String, Object>> perfil (@RequestHeader HttpHeaders headers, @PathVariable Optional<Long> id) {
        title = "Datos Reclutador";
        message = "";
        Long idExists = null;

        if (id.isPresent()) {
            idExists = id.get();
        } else {
            Usuario usuario = jwtTokenFilter.getUserDetails(headers);
            idExists = usuario.getId();
        }

        Optional<Reclutador> reclutadorDb = serviceImp.obtenerPorId(idExists);
        ReclutadorPojo reclutadorPojo = reclutadorDb.isPresent() && idExists != null ? modelMapper.map(reclutadorDb.get(), ReclutadorPojo.class) : null;
        
        if (reclutadorPojo != null)
            reclutadorPojo.setContrasena(null);
        else 
            message = String.format("El reclutador con el id %d no existe", idExists);


        return new ResponseEntity<>(response.buildStandardResponse(title, reclutadorPojo, message), HttpStatus.OK);
    }
}
