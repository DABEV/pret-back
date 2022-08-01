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
import mx.edu.utez.pret.model.Universidad;
import mx.edu.utez.pret.pojo.UniversidadPojo;
import mx.edu.utez.pret.service.UniversidadServiceImp;

@RestController
@RequestMapping("/universidad")
public class UniversidadController {

    @Autowired
    private UniversidadServiceImp serviceImp;

    @Autowired
    private CustomModelMapper modelMapper;

    @Autowired
    private Response response;

    @GetMapping("/obtener")
    public ResponseEntity<Map<String, Object>> obtenerTodos(){
        return new ResponseEntity<>(response.buildStandardResponse("Obtener todos las universidades",
         modelMapper.mapList(serviceImp.obtenerTodos(), UniversidadPojo.class)), HttpStatus.OK);
    }

    @GetMapping("/obtener/{id}")
    public ResponseEntity<Map<String, Object>> obtenerPorId (@PathVariable Optional<Long> id) {
        String title = "Obtener una universidad";
        Long idL = id.isPresent() ? id.get() : null;
        Optional<Universidad> universidad = serviceImp.obtenerPorId(idL);
        Map<String, Object> res = universidad.isPresent() 
            ? response.buildStandardResponse(title, universidad.get())
            : response.buildStandardResponse(title, null, String.format("La universidad con el id %d no existe", idL));
        
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    
}
