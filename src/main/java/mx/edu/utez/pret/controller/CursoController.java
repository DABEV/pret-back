package mx.edu.utez.pret.controller;

import mx.edu.utez.pret.config.CustomModelMapper;
import mx.edu.utez.pret.config.JwtTokenFilter;
import mx.edu.utez.pret.config.Response;
import mx.edu.utez.pret.model.Candidato;
import mx.edu.utez.pret.model.Curso;
import mx.edu.utez.pret.model.Usuario;
import mx.edu.utez.pret.pojo.CandidatoPojo;
import mx.edu.utez.pret.pojo.CursoPojo;
import mx.edu.utez.pret.service.CandidatoServiceImp;
import mx.edu.utez.pret.service.CursoServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/curso")
public class CursoController {

    @Autowired
    private CursoServiceImp serviceImp;

    @Autowired
    private CandidatoServiceImp serviceCandidatoImp;

    @Autowired
    private CustomModelMapper modelMapper;

    @Autowired
    private Response response;

    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    // Response default data
    private String title;
    private String message;
    private HttpStatus status;

    @GetMapping("/obtener/candidato")
    public ResponseEntity<Map<String, Object>> obtenerPorCandidato (@RequestHeader HttpHeaders headers) {
        Usuario usuario = jwtTokenFilter.getUserDetails(headers);
        Optional<Candidato> CandidatoDb = serviceCandidatoImp.obtenerPorId(usuario.getId());
        Candidato candidato = CandidatoDb.isPresent() ? CandidatoDb.get() : null;
        return new ResponseEntity<>(response.buildStandardResponse("Obtener los cursos de un candidato", modelMapper.mapList(serviceImp.obtenerPorCandidato(candidato), CursoPojo.class)), HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/registrar")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> registrar(@RequestHeader HttpHeaders headers, @Valid @RequestBody CursoPojo cursoPojo) {
        title = "Registrar curso";
        message = "Error al guardar el curso";
        CursoPojo answ = null;
        status = HttpStatus.OK;
        Usuario usuario = jwtTokenFilter.getUserDetails(headers);
        Optional<Candidato> candidatoDb = serviceCandidatoImp.obtenerPorId(usuario.getId());

        if (candidatoDb.isPresent()) {
            Candidato candidato = candidatoDb.get();
            cursoPojo.setCandidato(modelMapper.map(candidato, CandidatoPojo.class));
            Curso curso = modelMapper.map(cursoPojo, Curso.class);
            curso = serviceImp.guardar(curso);
            message = "El curso del candidato ha sido registrado exitosamente";
            status = HttpStatus.CREATED;
            answ = modelMapper.map(curso, CursoPojo.class);
        }
        return new ResponseEntity<>(response.buildStandardResponse(title, answ, message), status);
    }

    @Transactional
    @PostMapping("/actualizar")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> actualizar(@RequestHeader HttpHeaders headers,  @Valid @RequestBody CursoPojo cursoPojo) {
        title = "Actualizar curso";
        message = "No se realizo la actualización";
        CursoPojo answ = null;
        Curso curso = modelMapper.map(cursoPojo, Curso.class);
        Usuario usuario = jwtTokenFilter.getUserDetails(headers);
        Optional<Candidato> candidatoDb = serviceCandidatoImp.obtenerPorId(usuario.getId());
        Optional<Curso> cursoDb = serviceImp.obtenerPorId(curso.getId());

        if (candidatoDb.isPresent() && cursoDb.isPresent()) {
            Candidato candidato = candidatoDb.get();
            Curso cursoPresent = cursoDb.get();
            if (cursoPresent.getCandidato().getId().equals(candidato.getId())) {
                cursoPresent.setNombre(cursoPojo.getNombre());
                cursoPresent.setEmpresa(cursoPojo.getEmpresa());
                cursoPresent.setFechaObtencion(cursoPojo.getFechaObtencion());
                cursoPresent.setNumeroHoras(cursoPojo.getNumeroHoras());

                cursoPresent = serviceImp.guardar(cursoPresent);
                message = "Actualización exitosa";
                title = "Curso actualizado";
                answ = modelMapper.map(cursoPresent, CursoPojo.class);
            }

        }
        return new ResponseEntity<>(response.buildStandardResponse(title, answ, message), HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Map<String, Object>> eliminar(@RequestHeader HttpHeaders headers, @PathVariable Long id) {

        title = "Eliminar curso del candidato";
        message = "Error al eliminar el curso del candidato";
        status = HttpStatus.OK;

        Usuario usuario = jwtTokenFilter.getUserDetails(headers);
        Optional<Candidato> candidatoDb = serviceCandidatoImp.obtenerPorId(usuario.getId());
        Optional<Curso> cursoDb = serviceImp.obtenerPorId(id);

        if (candidatoDb.isPresent() && cursoDb.isPresent()) {
            Candidato candidato = candidatoDb.get();
            Curso curso = cursoDb.get();

            if (curso.getCandidato().getId().equals(candidato.getId())) {
                serviceImp.eliminar(curso.getId());
                message = "El curso ha sido eliminado satisfactoriamente";
            }
        }
        return new ResponseEntity<>(response.buildStandardResponse(title, message), status);
    }
}
