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
import mx.edu.utez.pret.model.Puesto;
import mx.edu.utez.pret.pojo.PuestoPojo;
import mx.edu.utez.pret.service.PuestoServiceImp;

@RestController
@RequestMapping("/puesto")
public class PuestoController {

    
    @Autowired
    private PuestoServiceImp serviceImp;

    @Autowired
    private CustomModelMapper modelMapper;

    @Autowired
    private Response response;

    @GetMapping("/obtener")
    public ResponseEntity<Map<String, Object>> obtenerTodos(){
        return new ResponseEntity<>(response.buildStandardResponse("Obtener todos los puestos",
         modelMapper.mapList(serviceImp.obtenerTodos(), PuestoPojo.class)), HttpStatus.OK);
    }

    @GetMapping("/obtener/{id}")
    public ResponseEntity<Map<String, Object>> obtenerPorId (@PathVariable Optional<Long> id) {
        String title = "Obtener un puesto";
        Long idL = id.isPresent() ? id.get() : null;
        Optional<Puesto> puesto = serviceImp.obtenerPorId(idL);
        Map<String, Object> res = puesto.isPresent() 
            ? response.buildStandardResponse(title, puesto.get())
            : response.buildStandardResponse(title, null, String.format("El puesto con el id %d no existe", idL));
        
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    
}
