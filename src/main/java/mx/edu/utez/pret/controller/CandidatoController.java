package mx.edu.utez.pret.controller;

import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import mx.edu.utez.pret.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
import mx.edu.utez.pret.model.Candidato;
import mx.edu.utez.pret.model.EstadoRepublica;
import mx.edu.utez.pret.model.Usuario;
import mx.edu.utez.pret.service.CandidatoServiceImp;
import mx.edu.utez.pret.service.RolServiceImp;

@RestController
@RequestMapping("/candidato")
public class CandidatoController {

    @Autowired
    private CandidatoServiceImp serviceImp;

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

    private static final String ROLE = "ROL_CANDIDATO"; 

    // Response default data
    private String title;
    private String message;
    private HttpStatus status;
    private Boolean data;

    @Transactional
    @PostMapping("/registrar")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> registrar (@Valid @RequestBody CandidatoPojo candidatoPojo) {
        title = "Registrar candidato";
        message = "Error al guardar el candidato";
        CandidatoPojo answ = null;
        status = HttpStatus.OK;
        Candidato candidato = modelMapper.map(candidatoPojo, Candidato.class);
        
        candidato.setHabilitado(true);
        // Encrypt password
        candidato.setContrasena(passwordEncoder.encode(candidato.getContrasena()));
        // Set Candidato Role
        candidato.addRol(rolServiceImp.buscarPorNombre(ROLE));
        candidato = serviceImp.guardar(candidato);

        if (candidato != null && candidato.getId() != null) {
            answ = modelMapper.map(candidato, CandidatoPojo.class);
            title = "Candidato registrado";
            message = "El candidato ha sido registrado satisfactoriamente";
            status = HttpStatus.CREATED;
        }

        return new ResponseEntity<>(response.buildStandardResponse(title, answ, message), status);
    }

    @Transactional
    @PostMapping("/actualizar")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> actualizar (@RequestHeader HttpHeaders headers, @Valid @RequestBody CandidatoPojo candidatoPojo) {
        title = "Actualizar Candidato";
        message = "No se realizo la actualización";
        CandidatoPojo answ = null;
        Usuario usuario = jwtTokenFilter.getUserDetails(headers);
        Optional<Candidato> candidatoDb = serviceImp.obtenerPorId(usuario.getId());
        
        if (candidatoDb.isPresent() && usuario.getId() != null) {
            Candidato candidato = candidatoDb.get();

            candidato.setNombre(candidatoPojo.getNombre());
            candidato.setApellidoPaterno(candidatoPojo.getApellidoPaterno());
            candidato.setApellidoMaterno(candidatoPojo.getApellidoMaterno());
            candidato.setTelefono(candidatoPojo.getTelefono());
            candidato.setFechaNacimiento(candidatoPojo.getFechaNacimiento());
            candidato.setEstadoRepublica(modelMapper.map(candidatoPojo.getEstadoRepublica(), EstadoRepublica.class));
            candidato.setTituloCurricular(candidatoPojo.getTituloCurricular());
            candidato.setDescripcionPerfil(candidatoPojo.getDescripcionPerfil());
            candidato.setFoto(candidatoPojo.getFoto());
            candidato.setConocimientosHabilidades(candidatoPojo.getConocimientosHabilidades());

            candidato = serviceImp.guardar(candidato);
            title = "Candidato actualizado";
            message = "Actualización exitosa";
            answ = modelMapper.map(candidato, CandidatoPojo.class);
        }

        return new ResponseEntity<>(response.buildStandardResponse(title, answ, message), HttpStatus.OK);
    }

    @GetMapping(value = {"/perfil", "/perfil/{id}"})
    @ResponseBody
    public ResponseEntity<Map<String, Object>> perfil (@RequestHeader HttpHeaders headers, @PathVariable Optional<Long> id) {
        title = "Datos Candidato";
        message = "";
        Long idExists;

        if (id.isPresent()) {
            idExists = id.get();
        } else {
            Usuario usuario = jwtTokenFilter.getUserDetails(headers);
            idExists = usuario.getId();
        }
        
        Optional<Candidato> candidatoDb = serviceImp.obtenerPorId(idExists);
        Candidato candidato = candidatoDb.isPresent() ? candidatoDb.get() : null;
        CandidatoPojo candidatoPojo =  candidato != null  ? modelMapper.map(candidato, CandidatoPojo.class) : null;
        
        if (candidatoPojo == null || candidatoPojo.getId() == null)
            message = String.format("El candidato con el id %d no existe", idExists);

        return new ResponseEntity<>(response.buildStandardResponse(title, candidatoPojo, message), HttpStatus.OK);
    }

    @PostMapping("/actualizar-foto")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> actualizarFoto (@RequestHeader HttpHeaders headers, @Valid @RequestBody AuthUploadPhotoPojo request) {
        title = "Actualizar foto del candidato";
        message = "No se realizo la actualización";
        data = false;
        Usuario usuario = jwtTokenFilter.getUserDetails(headers);
        Optional<Candidato> candidatoDb = serviceImp.obtenerPorId(usuario.getId());
        
        if (candidatoDb.isPresent() && usuario.getId() != null) {
            Candidato candidato = candidatoDb.get();

            candidato.setFoto(request.getFoto());

            title = "Foto del candidato actualizada";
            message = "Actualización exitosa";
            data = serviceImp.guardar(candidato) != null;
        }

        return new ResponseEntity<>(response.buildStandardResponse(title, data, message), HttpStatus.OK);
    }
}
