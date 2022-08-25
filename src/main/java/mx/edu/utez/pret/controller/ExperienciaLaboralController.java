package mx.edu.utez.pret.controller;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

        // Valida las fechas de la experiencia
        LocalDate now = LocalDate.now();
        Boolean flag =
                experienciaLaboralPojo.getFechaInicio().isBefore(now) &&
                        // En caso de que exista la fecha de fin, la agregamos a la validación.
                        (experienciaLaboralPojo.getFechaFin() != null ? experienciaLaboralPojo.getFechaInicio().isBefore(experienciaLaboralPojo.getFechaFin()) : true);

        if (!flag)
            message = "Favor de verificar las fechas de la experiencia.";

        if (candidatoDb.isPresent() && flag) {
            Candidato candidato = candidatoDb.get();
            experienciaLaboral.setCandidato(candidato);
            experienciaLaboral = serviceExperienciaLaboralImp.guardar(experienciaLaboral);
            answ = modelMapper.map(experienciaLaboral, ExperienciaLaboralPojo.class);
            message = "La experiencia laboral de candidato ha sido registrado satisfactoriamente";
        }

        return new ResponseEntity<>(response.buildStandardResponse(title, answ, message), status);
    }

    @Transactional
    @PostMapping("/actualizar")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> actualizar(@RequestHeader HttpHeaders headers,
            @Valid @RequestBody ExperienciaLaboralPojo experienciaLaboralPojo) {

        title = "Actualizar experiencia laboral del candidato";
        message = "Error al actualizar la experiencia laboral del candidato";
        status = HttpStatus.OK;
        ExperienciaLaboralPojo answ = null;

        ExperienciaLaboral experienciaLaboral = modelMapper.map(experienciaLaboralPojo, ExperienciaLaboral.class);

        Usuario usuario = jwtTokenFilter.getUserDetails(headers);
        Optional<Candidato> candidatoDb = serviceCandidatoImp.obtenerPorId(usuario.getId());
        Optional<ExperienciaLaboral> expLaboralDb = serviceExperienciaLaboralImp
                .obtenerPorId(experienciaLaboral.getId());

        // Valida las fechas de la experiencia
        LocalDate now = LocalDate.now();
        Boolean flag =
                experienciaLaboralPojo.getFechaInicio().isBefore(now) &&
                // En caso de que exista la fecha de fin, la agregamos a la validación.
                (experienciaLaboralPojo.getFechaFin() != null ? experienciaLaboralPojo.getFechaInicio().isBefore(experienciaLaboralPojo.getFechaFin()) : true);

        if (!flag)
            message = "Favor de verificar las fechas de la experiencia.";

        if (candidatoDb.isPresent() && expLaboralDb.isPresent() && flag) {
            Candidato candidato = candidatoDb.get();
            ExperienciaLaboral experienciaLab = expLaboralDb.get();

            if (experienciaLab.getCandidato().getId() == candidato.getId()) {

                experienciaLab.setCandidato(candidato);
                experienciaLab.setActividadesRealizadas(experienciaLaboralPojo.getActividadesRealizadas());
                experienciaLab.setFechaFin(experienciaLaboralPojo.getFechaFin());
                experienciaLab.setFechaInicio(experienciaLaboralPojo.getFechaInicio());
                experienciaLab.setPuesto(experienciaLaboralPojo.getPuesto());

                experienciaLab = serviceExperienciaLaboralImp.guardar(experienciaLab);

                answ = modelMapper.map(experienciaLab, ExperienciaLaboralPojo.class);
                message = "La experiencia laboral de candidato ha sido actualizada satisfactoriamente";
            }
        }

        return new ResponseEntity<>(response.buildStandardResponse(title, answ, message), status);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Map<String, Object>> eliminar(@RequestHeader HttpHeaders headers,
            @PathVariable Long id) {

        title = "Eliminar experiencia laboral del candidato";
        message = "Error al eliminar la experiencia laboral del candidato";
        status = HttpStatus.OK;

        Usuario usuario = jwtTokenFilter.getUserDetails(headers);
        Optional<Candidato> candidatoDb = serviceCandidatoImp.obtenerPorId(usuario.getId());
        Optional<ExperienciaLaboral> expLaboralDb = serviceExperienciaLaboralImp.obtenerPorId(id);

        if (candidatoDb.isPresent() && expLaboralDb.isPresent()) {
            Candidato candidato = candidatoDb.get();
            ExperienciaLaboral experienciaLaboral = expLaboralDb.get();

            if (experienciaLaboral.getCandidato().getId() == candidato.getId()) {
                serviceExperienciaLaboralImp.eliminar(experienciaLaboral.getId());
                message = "La experiencia laboral ha sido eliminado satisfactoriamente";

            }

        }

        return new ResponseEntity<>(response.buildStandardResponse(title, message), status);

    }

}
