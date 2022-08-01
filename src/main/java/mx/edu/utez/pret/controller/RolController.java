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
import mx.edu.utez.pret.model.Rol;
import mx.edu.utez.pret.pojo.RolPojo;
import mx.edu.utez.pret.service.RolServiceImp;

@RestController
@RequestMapping("/rol")
public class RolController {

    @Autowired
    private RolServiceImp serviceImp;

    @Autowired
    private CustomModelMapper modelMapper;

    @Autowired
    private Response response;

    @GetMapping("/obtener")
    public ResponseEntity<Map<String, Object>> obtenerTodos(){
        return new ResponseEntity<>(response.buildStandardResponse("Obtener todos los roles",
         modelMapper.mapList(serviceImp.obtenerTodos(), RolPojo.class)), HttpStatus.OK);
    }

    @GetMapping("/obtener/{id}")
    public ResponseEntity<Map<String, Object>> obtenerPorId (@PathVariable Optional<Long> id) {
        String title = "Obtener un rol";
        Long idL = id.isPresent() ? id.get() : null;
        Optional<Rol> rol = serviceImp.obtenerPorId(idL);
        Map<String, Object> res = rol.isPresent() 
            ? response.buildStandardResponse(title, rol.get())
            : response.buildStandardResponse(title, null, String.format("El rol con el id %d no existe", idL));
        
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    
}
