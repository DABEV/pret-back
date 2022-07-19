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
import mx.edu.utez.pret.model.EstadoRepublica;
import mx.edu.utez.pret.pojo.EstadoRepublicaPojo;
import mx.edu.utez.pret.service.EstadoRepublicaServiceImp;

@RestController
@RequestMapping("/estado-republica")
public class EstadoRepublicaController {

    @Autowired
    private EstadoRepublicaServiceImp serviceImp;

    @Autowired
    private CustomModelMapper modelMapper;

    @Autowired
    private Response response;

    @GetMapping("/obtener")
    public ResponseEntity<Map<String, Object>> obtenerTodos(){
        return new ResponseEntity<>(response.buildStandardResponse("Obtener todos los estados de la república",
         modelMapper.mapList(serviceImp.obtenerTodos(), EstadoRepublicaPojo.class)), HttpStatus.OK);
    }

    @GetMapping("/obtener/{id}")
    public ResponseEntity<Map<String, Object>> obtenerPorId (@PathVariable Optional<Long> id) {
        String title = "Obtener un estado de la república";
        Long idL = id.isPresent() ? id.get() : null;
        Optional<EstadoRepublica> estadoRepublica = serviceImp.obtenerPorId(idL);
        Map<String, Object> res = estadoRepublica.isPresent() 
            ? response.buildStandardResponse(title, estadoRepublica.get())
            : response.buildStandardResponse(title, null, String.format("El estado de la republica con el id %d no existe", idL));
        
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    
}
