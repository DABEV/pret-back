package mx.edu.utez.pret.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import mx.edu.utez.pret.config.CustomModelMapper;
import mx.edu.utez.pret.config.Response;
import mx.edu.utez.pret.model.Candidato;
import mx.edu.utez.pret.pojo.CandidatoPojo;
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

    private static final String ROLE = "ROL_CANDIDATO"; 

    @PostMapping("/registrar")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> registrar (@Valid @RequestBody CandidatoPojo candidatoPojo) {
        candidatoPojo.setHabilitado(true);

        // Encrypt password
        candidatoPojo.setContrasena(passwordEncoder.encode(candidatoPojo.getContrasena()));

        Candidato candidato = modelMapper.map(candidatoPojo, Candidato.class);

        // Set Candidato Role
        candidato.addRol(rolServiceImp.buscarPorNombre(ROLE));

        CandidatoPojo answ = modelMapper.map(serviceImp.guardar(candidato), CandidatoPojo.class);

        return new ResponseEntity<>(response.buildStandardResponse("Candidato registrado", answ, "El candidato ha sido registrado satisfactoriamente"), HttpStatus.CREATED);
    }
}
