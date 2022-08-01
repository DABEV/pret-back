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
import mx.edu.utez.pret.model.EstadoVacante;
import mx.edu.utez.pret.pojo.EstadoVacantePojo;
import mx.edu.utez.pret.service.EstadoVacanteServiceImp;

@RestController
@RequestMapping("/estado-vacante")
public class EstadoVacanteController {
    
    @Autowired
    private EstadoVacanteServiceImp serviceImp;

    @Autowired
    private CustomModelMapper modelMapper;

    @Autowired
    private Response response;
    

    @GetMapping("/obtener")
    public ResponseEntity<Map<String, Object>> obtenerTodos(){
        return new ResponseEntity<>(response.buildStandardResponse("Obtener todos los estados de la vacante",
         modelMapper.mapList(serviceImp.obtenerTodos(), EstadoVacantePojo.class)), HttpStatus.OK);
    }

    @GetMapping("/obtener/{id}")
    public ResponseEntity<Map<String, Object>> obtenerPorId (@PathVariable Optional<Long> id) {
        String title = "Obtener un estado de vacante";
        Long idL = id.isPresent() ? id.get() : null;
        Optional<EstadoVacante> estadoVacante = serviceImp.obtenerPorId(idL);
        Map<String, Object> res = estadoVacante.isPresent() 
            ? response.buildStandardResponse(title, estadoVacante.get())
            : response.buildStandardResponse(title, null, String.format("El estado de vacante con el id %d no existe", idL));
        
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
