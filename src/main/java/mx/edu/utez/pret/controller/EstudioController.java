package mx.edu.utez.pret.controller;

import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import mx.edu.utez.pret.model.Estudio;
import mx.edu.utez.pret.model.Universidad;
import mx.edu.utez.pret.model.Usuario;
import mx.edu.utez.pret.pojo.EstudioPojo;
import mx.edu.utez.pret.service.CandidatoServiceImp;
import mx.edu.utez.pret.service.EstudioServiceImp;
import mx.edu.utez.pret.service.UniversidadServiceImp;

@RestController
@RequestMapping("/estudio")
public class EstudioController {

    @Autowired
    private EstudioServiceImp serviceImp;

    @Autowired
    private CandidatoServiceImp serviceCandidatoImp;

    @Autowired
    private UniversidadServiceImp serviceUniversidadImp;

    @Autowired
    private CustomModelMapper modelMapper;

    @Autowired
    private Response response;

    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    // Response default data
    private String title;
    private String message;
    private HttpStatus status;

    @Transactional
    @PostMapping("/registrar")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> registrar(@RequestHeader HttpHeaders headers,
            @Valid @RequestBody EstudioPojo estudioPojo) {
        title = "Registrar estudio";
        message = "Error al guardar el estudio";
        EstudioPojo answ = null;
        status = HttpStatus.OK;
        Estudio estudio = modelMapper.map(estudioPojo, Estudio.class);
        Usuario usuario = jwtTokenFilter.getUserDetails(headers);
        Optional<Candidato> candidatoDb = serviceCandidatoImp.obtenerPorId(usuario.getId());
        Optional<Universidad> universidadDb = serviceUniversidadImp.obtenerPorId(estudio.getUniversidad().getId());

        if (candidatoDb.isPresent() && universidadDb.isPresent()) {
            Candidato candidato = candidatoDb.get();
            Universidad universidad = universidadDb.get();
            estudio.setCandidato(candidato);
            estudio.setUniversidad(universidad);
            estudio = serviceImp.guardar(estudio);
            message = "El estudio de candidato ha sido registrado exitosamente";
            status = HttpStatus.CREATED;
            answ = modelMapper.map(estudio, EstudioPojo.class);
        }
        return new ResponseEntity<>(response.buildStandardResponse(title, answ, message), status);
    }

    @Transactional
    @PostMapping("/actualizar")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> actualizar(@RequestHeader HttpHeaders headers,
            @Valid @RequestBody EstudioPojo estudioPojo) {
        title = "Actualizar estudio";
        message = "No se realizo la actualización";
        EstudioPojo answ = null;
        Estudio estudio = modelMapper.map(estudioPojo, Estudio.class);
        Usuario usuario = jwtTokenFilter.getUserDetails(headers);
        Optional<Candidato> candidatoDb = serviceCandidatoImp.obtenerPorId(usuario.getId());
        Optional<Estudio> estudioDb = serviceImp.obtenerPorId(estudio.getId());
        Optional<Universidad> universidadDb = serviceUniversidadImp.obtenerPorId(estudio.getUniversidad().getId());


        if (candidatoDb.isPresent() && estudioDb.isPresent() && universidadDb.isPresent()) {
            Candidato candidato = candidatoDb.get();
            Estudio estudioPresent = estudioDb.get();
            Universidad universidad = universidadDb.get();
            if (estudioPresent.getCandidato().getId().equals(candidato.getId())) {
                estudioPresent.setCandidato(candidato);
                estudioPresent.setCarrera(estudioPojo.getCarrera());
                estudioPresent.setFechaFin(estudioPojo.getFechaFin());
                estudioPresent.setFechaInicio(estudioPojo.getFechaInicio());
                estudioPresent.setGradoAcademico(estudioPojo.getGradoAcademico());
                estudioPresent.setUniversidad(universidad);
                estudioPresent = serviceImp.guardar(estudioPresent);
                message = "Actualización exitosa";
                title = "Estudio actualizado";
                answ = modelMapper.map(estudioPresent, EstudioPojo.class);
            }

        }
        return new ResponseEntity<>(response.buildStandardResponse(title, answ, message), HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Map<String, Object>> eliminar(@RequestHeader HttpHeaders headers,
            @PathVariable Long id) {

        title = "Eliminar estudio del candidato";
        message = "Error al eliminar el estudio del candidato";
        status = HttpStatus.OK;

        Usuario usuario = jwtTokenFilter.getUserDetails(headers);
        Optional<Candidato> candidatoDb = serviceCandidatoImp.obtenerPorId(usuario.getId());
        Optional<Estudio> estudioDb = serviceImp.obtenerPorId(id);

        if (candidatoDb.isPresent() && estudioDb.isPresent()) {
            Candidato candidato = candidatoDb.get();
            Estudio estudio = estudioDb.get();

            if (estudio.getCandidato().getId().equals(candidato.getId())) {
                serviceImp.eliminar(estudio.getId());
                message = "El estudio ha sido eliminado satisfactoriamente";

            }

        }

        return new ResponseEntity<>(response.buildStandardResponse(title, message), status);

    }

}
