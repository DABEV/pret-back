package mx.edu.utez.pret.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpHeaders;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import mx.edu.utez.pret.model.Idioma;
import mx.edu.utez.pret.model.IdiomaCandidato;
import mx.edu.utez.pret.model.Usuario;
import mx.edu.utez.pret.pojo.IdiomaCandidatoPojo;
import mx.edu.utez.pret.service.CandidatoServiceImp;
import mx.edu.utez.pret.service.IdiomaCandidatoServiceImp;
import mx.edu.utez.pret.service.IdiomaServiceImp;

@RestController
@RequestMapping("/idioma-candidato")
public class IdiomaCandidatoController {

    @Autowired
    private Response response;

    @Autowired
    private IdiomaCandidatoServiceImp serviceImp;

    @Autowired
    private CustomModelMapper modelMapper;

    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    @Autowired
    private CandidatoServiceImp serviceCandidatoImp;

    @Autowired
    private IdiomaServiceImp idiomaServiceImp;

    // Response default data
    private String title;
    private String message;
    private HttpStatus status;

    @Transactional
    @PostMapping("/registrar")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> registrar(@RequestHeader HttpHeaders headers,
            @Valid @RequestBody IdiomaCandidatoPojo idiomaCandidatoPojo) {

        title = "Registrar idioma del candidato";
        message = "Error al guardar el idioma del candidato";
        status = HttpStatus.OK;

        IdiomaCandidatoPojo idiomaPojo = null;
        IdiomaCandidato idiomaCandidato = modelMapper.map(idiomaCandidatoPojo, IdiomaCandidato.class);
        Usuario usuario = jwtTokenFilter.getUserDetails(headers);
        Optional<Candidato> candidatoDb = serviceCandidatoImp.obtenerPorId(usuario.getId());
        Optional<Idioma> idiomaDb = idiomaServiceImp.obtenerPorId(idiomaCandidato.getId().getIdiomaId());

        if (candidatoDb.isPresent() && idiomaDb.isPresent()) {
            Candidato candidato = candidatoDb.get();
            Idioma idiomaObtenido = idiomaDb.get();
            idiomaCandidato.setCandidato(candidato);
            idiomaCandidato.setIdioma(idiomaObtenido);
            idiomaCandidato = serviceImp.guardar(idiomaCandidato);
            message = "El idioma de candidato ha sido registrado satisfactoriamente";
            status = HttpStatus.CREATED;
            idiomaPojo = modelMapper.map(idiomaCandidato, IdiomaCandidatoPojo.class);

        }
        return new ResponseEntity<>(response.buildStandardResponse(title, idiomaPojo, message), status);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Map<String, Object>> eliminar(@RequestHeader HttpHeaders headers,
            @PathVariable Long id) {

        title = "Eliminar idioma del candidato";
        message = "Error al eliminar el idioma del candidato";
        status = HttpStatus.OK;

        Usuario usuario = jwtTokenFilter.getUserDetails(headers);
        Optional<Candidato> candidatoDb = serviceCandidatoImp.obtenerPorId(usuario.getId());
        Optional<Idioma> idiomaDb = idiomaServiceImp.obtenerPorId(id);
        if (candidatoDb.isPresent() && idiomaDb.isPresent()) {
            Candidato candidato = candidatoDb.get();
            Idioma idioma = idiomaDb.get();
            serviceImp.deleteByCandidatoAndIdioma(candidato, idioma);
            message = "El idioma del candidato ha sido eliminado satisfactoriamente";

        }
        return new ResponseEntity<>(response.buildStandardResponse(title, message), status);

    }

}
