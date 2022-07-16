package mx.edu.utez.pret.controller;

import java.util.Map;
import java.util.Optional;

import mx.edu.utez.pret.config.CustomModelMapper;
import mx.edu.utez.pret.config.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.edu.utez.pret.model.Beneficio;
import mx.edu.utez.pret.pojo.BeneficioPojo;
import mx.edu.utez.pret.service.BeneficioServiceImp;

@RestController
@RequestMapping("/beneficio")
public class BeneficioController {

    @Autowired
    private BeneficioServiceImp serviceImp;

    @Autowired
    private CustomModelMapper modelMapper;

    @Autowired
    private Response response;
    
    @GetMapping("/obtener")
    public ResponseEntity<Map<String, Object>> obtenerTodos () {
        return new ResponseEntity<>(response.buildStandarResponse("Obtener todos los beneficios", modelMapper.mapList(serviceImp.obtenerTodos(), BeneficioPojo.class)), HttpStatus.OK);
    }

    @GetMapping("/obtener/{id}")
    public ResponseEntity<Map<String, Object>> obtenerPorId (@PathVariable Optional<Long> id) {
        String title = "Obtener un beneficio";
        Long idL = id.get();
        Optional<Beneficio> beneficio = serviceImp.obtenerPorId(idL);
        Map<String, Object> res = beneficio.isPresent() 
            ? response.buildStandarResponse(title, beneficio.get())
            : response.buildStandarResponse(title, null, String.format("El beneficio con el id %d no existe", idL));
        
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
