package mx.edu.utez.pret.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.edu.utez.pret.config.CustomModelMapper;
import mx.edu.utez.pret.config.Response;
import mx.edu.utez.pret.model.Idioma;
import mx.edu.utez.pret.pojo.IdiomaPojo;
import mx.edu.utez.pret.service.IdiomaServiceImp;

@RestController
@RequestMapping("/idioma")
public class IdiomaController {

    @Autowired
    private IdiomaServiceImp serviceImp;

    @Autowired
    private CustomModelMapper modelMapper;

    @Autowired
    private Response response;

    @GetMapping("/obtener")
    public ResponseEntity<Map<String, Object>> obtenerTodos(){
        return new ResponseEntity<>(response.buildStandardResponse("Obtener todos los idiomas",
         modelMapper.mapList(serviceImp.obtenerTodos(), IdiomaPojo.class)), HttpStatus.OK);
    }

    @GetMapping("/obtener/{id}")
    public ResponseEntity<Map<String, Object>> obtenerPorId (@PathVariable Optional<Long> id) {
        String title = "Obtener un idioma";
        Long idL = id.isPresent() ? id.get() : null;
        Optional<Idioma> idioma = serviceImp.obtenerPorId(idL);
        Map<String, Object> res = idioma.isPresent() 
            ? response.buildStandardResponse(title, idioma.get())
            : response.buildStandardResponse(title, null, String.format("El idioma con el id %d no existe", idL));
        
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    
}
