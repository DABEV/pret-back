package mx.edu.utez.pret.controller;

import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import mx.edu.utez.pret.model.ExperienciaLaboral;
import mx.edu.utez.pret.model.Usuario;
import mx.edu.utez.pret.pojo.ExperienciaLaboralPojo;
import mx.edu.utez.pret.service.CandidatoServiceImp;
import mx.edu.utez.pret.service.ExperienciaLaboralServiceImp;

@RestController
@RequestMapping("/experiencia-laboral")
public class ExperienciaLaboralController {

    @Autowired
    private Response response;

    @Autowired
    private CustomModelMapper modelMapper;

    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    @Autowired
    private ExperienciaLaboralServiceImp serviceExperienciaLaboralImp;

    @Autowired
    private CandidatoServiceImp serviceCandidatoImp;

    // Response default data
    private String title;
    private String message;
    private HttpStatus status;

    @Transactional
    @PostMapping("/registrar")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> registrar(@RequestHeader HttpHeaders headers,
            @Valid @RequestBody ExperienciaLaboralPojo experienciaLaboralPojo) {

        title = "Registrar experiencia laboral del candidato";
        message = "Error al guardar la experiencia laboral del candidato";
        status = HttpStatus.OK;
        ExperienciaLaboralPojo answ = null;

        ExperienciaLaboral experienciaLaboral = modelMapper.map(experienciaLaboralPojo, ExperienciaLaboral.class);

        Usuario usuario = jwtTokenFilter.getUserDetails(headers);
        Optional<Candidato> candidatoDb = serviceCandidatoImp.obtenerPorId(usuario.getId());

        if(candidatoDb.isPresent()){
            Candidato candidato = candidatoDb.get();
            experienciaLaboral.setCandidato(candidato);
            experienciaLaboral = serviceExperienciaLaboralImp.guardar(experienciaLaboral);
            answ = modelMapper.map(experienciaLaboral, ExperienciaLaboralPojo.class);
            message = "La experiencia laboral de candidato ha sido registrado satisfactoriamente";
        }

        return new ResponseEntity<>(response.buildStandardResponse(title, answ, message), status);
    }

}
