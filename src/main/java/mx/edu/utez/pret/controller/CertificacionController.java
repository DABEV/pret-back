package mx.edu.utez.pret.controller;

import mx.edu.utez.pret.config.CustomModelMapper;
import mx.edu.utez.pret.config.JwtTokenFilter;
import mx.edu.utez.pret.config.Response;
import mx.edu.utez.pret.model.Candidato;
import mx.edu.utez.pret.model.Certificacion;
import mx.edu.utez.pret.model.Usuario;
import mx.edu.utez.pret.pojo.CandidatoPojo;
import mx.edu.utez.pret.pojo.CertificacionPojo;
import mx.edu.utez.pret.service.CandidatoServiceImp;
import mx.edu.utez.pret.service.CertificacionServiceImp;
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
@RequestMapping("/certificacion")
public class CertificacionController {

    @Autowired
    private CertificacionServiceImp serviceImp;

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
        return new ResponseEntity<>(response.buildStandardResponse("Obtener las certificaciones de un candidato", modelMapper.mapList(serviceImp.obtenerPorCandidato(candidato), CertificacionPojo.class)), HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/registrar")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> registrar(@RequestHeader HttpHeaders headers, @Valid @RequestBody CertificacionPojo certificacionPojo) {
        title = "Registrar certificación";
        message = "Error al guardar la certificación";
        CertificacionPojo answ = null;
        status = HttpStatus.OK;
        Usuario usuario = jwtTokenFilter.getUserDetails(headers);
        Optional<Candidato> candidatoDb = serviceCandidatoImp.obtenerPorId(usuario.getId());

        if (candidatoDb.isPresent()) {
            Candidato candidato = candidatoDb.get();
            certificacionPojo.setCandidato(modelMapper.map(candidato, CandidatoPojo.class));
            Certificacion certificacion = modelMapper.map(certificacionPojo, Certificacion.class);
            certificacion = serviceImp.guardar(certificacion);
            message = "La certificacion del candidato ha sido registrado exitosamente";
            status = HttpStatus.CREATED;
            answ = modelMapper.map(certificacion, CertificacionPojo.class);
        }
        return new ResponseEntity<>(response.buildStandardResponse(title, answ, message), status);
    }

    @Transactional
    @PostMapping("/actualizar")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> actualizar(@RequestHeader HttpHeaders headers,  @Valid @RequestBody CertificacionPojo certificacionPojo) {
        title = "Actualizar certificación";
        message = "No se realizo la actualización";
        CertificacionPojo answ = null;
        Certificacion certificacion = modelMapper.map(certificacionPojo, Certificacion.class);
        Usuario usuario = jwtTokenFilter.getUserDetails(headers);
        Optional<Candidato> candidatoDb = serviceCandidatoImp.obtenerPorId(usuario.getId());
        Optional<Certificacion> certificacionDb = serviceImp.obtenerPorId(certificacion.getId());

        if (candidatoDb.isPresent() && certificacionDb.isPresent()) {
            Candidato candidato = candidatoDb.get();
            Certificacion certificacionPresent = certificacionDb.get();
            if (certificacionPresent.getCandidato().getId().equals(candidato.getId())) {
                certificacionPresent.setNombre(certificacionPojo.getNombre());
                certificacionPresent.setEmpresa(certificacionPojo.getEmpresa());
                certificacionPresent.setFechaObtencion(certificacionPojo.getFechaObtencion());
                certificacionPresent.setFechaCaducidad(certificacionPojo.getFechaCaducidad());

                certificacionPresent = serviceImp.guardar(certificacionPresent);
                message = "Actualización exitosa";
                title = "Certificacion actualizado";
                answ = modelMapper.map(certificacionPresent, CertificacionPojo.class);
            }

        }
        return new ResponseEntity<>(response.buildStandardResponse(title, answ, message), HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Map<String, Object>> eliminar(@RequestHeader HttpHeaders headers, @PathVariable Long id) {

        title = "Eliminar certificación del candidato";
        message = "Error al eliminar el certificación del candidato";
        status = HttpStatus.OK;

        Usuario usuario = jwtTokenFilter.getUserDetails(headers);
        Optional<Candidato> candidatoDb = serviceCandidatoImp.obtenerPorId(usuario.getId());
        Optional<Certificacion> certificacionDb = serviceImp.obtenerPorId(id);

        if (candidatoDb.isPresent() && certificacionDb.isPresent()) {
            Candidato candidato = candidatoDb.get();
            Certificacion certificacion = certificacionDb.get();

            if (certificacion.getCandidato().getId().equals(candidato.getId())) {
                serviceImp.eliminar(certificacion.getId());
                message = "La certificación ha sido eliminado satisfactoriamente";
            }
        }
        return new ResponseEntity<>(response.buildStandardResponse(title, message), status);
    }
}
