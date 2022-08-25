package mx.edu.utez.pret.controller;

import mx.edu.utez.pret.config.CustomModelMapper;
import mx.edu.utez.pret.config.JwtTokenFilter;
import mx.edu.utez.pret.config.Response;
import mx.edu.utez.pret.model.*;
import mx.edu.utez.pret.pojo.MailRequestPojo;
import mx.edu.utez.pret.pojo.PostulacionPojo;
import mx.edu.utez.pret.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/postulacion")
public class PostulacionController {

    private final String VACANTE_INFO =
            "<h3>Información de la vacante</h3> <br /> <br /> <b>Nombre:</b> %s <br />" +
            "<b>Tipo:</b> %s <br />" +
            "<b>Modalidad:</b> %s <br />" +
            "<b>Periodo de pago:</b> %s <br />" +
            "<b>Descripción:</b> <br /> <p>%s</p> <br /> <br />" +
            "<h3><a href=\"%s\">Haz clic aquí para visitar la vacante</a></h3> <br /> <br />";

    private Map<Long, String> messages = Map.of(
            1L, "<h3>En caso de no haberte postulado, favor de contactar al administrador.</h3> <br /> <br /><h3>Este correo es de confirmación, te has postulado a la siguiente vacante:</h3> <br />",
            2L, "<h3>Su CV ha sido visto por la empresa, favor de estar atento a su teléfono y correo electrónico</h3> <br />",
            3L, "<h3>Ha sido seleccionado para la entrevista, favor de estar atento a su correo electrónico, en breve le harán llegar los detalles.</h3> <br />",
            4L, "<h3>Ha sido seleccionado como candidato idóneo, esto no quiere decir que el puesto es suyo, debe de esperar a recibir un correo electrónico de que usted ha sido aceptado para el puesto.</h3> <br />",
            5L, "<h3>¡Muchas Felicidades!, usted ha sido contratado.</h3> <br />",
            6L, "<h3>Lo sentimos mucho, pero usted ha sido rechazado para la vacante, no te desanimes, hay miles de empresas esperando por tu talento, no te rindas.</h3> <br />"
    );

    @Value("${app.front-end}")
    private String urlFrontEnd;

    @Value("${spring.mail.username}")
    private String emailFrom;

    @Autowired
    private PostulacionServiceImp serviceImp;

    @Autowired
    private VacanteServiceImp vacanteServiceImp;

    @Autowired
    private EstadoVacanteServiceImp estadoVacanteServiceImp;

    @Autowired
    private CandidatoServiceImp candidatoServiceImp;

    @Autowired
    private EmailService emailService;

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
    private Boolean data;

    @GetMapping("/obtener/vacante/{id}")
    public ResponseEntity<Map<String, Object>> obtenerPorVacante (@RequestHeader HttpHeaders headers, @PathVariable Optional<Long> id) {
        title = "Obtener las postulaciones de una vacante";
        message = "Error al obtener las postulaciones de la vacante";

        Set<PostulacionPojo> postulaciones = null;
        Usuario usuario = jwtTokenFilter.getUserDetails(headers);
        Optional<Vacante> vacanteDb = vacanteServiceImp.obtenerPorId(id.isPresent() ? id.get() : null);
        Vacante vacante = null;

        if (vacanteDb.isPresent())
            vacante = vacanteDb.get();

        if (vacante != null && vacante.getReclutador().getId().equals(usuario.getId()))
            postulaciones = modelMapper.mapSet(vacante.getPostulaciones(), PostulacionPojo.class);

        return new ResponseEntity<>(response.buildStandardResponse(title, postulaciones, message), HttpStatus.OK);
    }

    @GetMapping("/obtener/candidato")
    public ResponseEntity<Map<String, Object>> obtenerPorCandidato (@RequestHeader HttpHeaders headers) {
        title = "Obtener las postulaciones de un candidato";
        message = "Error al obtener las postulaciones de un candidato";

        List<PostulacionPojo> postulaciones = null;
        Usuario usuario = jwtTokenFilter.getUserDetails(headers);

        Optional<Candidato> candidatoDp = candidatoServiceImp.obtenerPorId(usuario.getId());
        
        if (candidatoDp.isPresent()) {
            postulaciones = modelMapper.mapList(serviceImp.obtenerPorCandidato(candidatoDp.get()), PostulacionPojo.class);
            message = "Se han obtenido exitosamente";
        }

        return new ResponseEntity<>(response.buildStandardResponse(title, postulaciones, message), HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/registrar")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> registrar (@RequestHeader HttpHeaders headers, @Valid @RequestBody PostulacionPojo postulacionPojo) {
        title = "Postularse a una vacante";
        message = "Error al postularse a una vacante";
        status = HttpStatus.OK;

        Usuario usuario = jwtTokenFilter.getUserDetails(headers);
        Optional<Candidato> candidatoDb = candidatoServiceImp.obtenerPorId(usuario.getId());

        // Verifica que el usuario no este postulado
        Optional<Postulacion> postulacionDb = serviceImp.obtenerPorId(new PostulacionId(usuario.getId(), postulacionPojo.getId().getVacanteId()));

        if (postulacionDb.isPresent())
            message = "Usted ya esta postulado a esta vacante";

        if (candidatoDb.isPresent() && !postulacionDb.isPresent()) {
            Postulacion postulacion = modelMapper.map(postulacionPojo, Postulacion.class);
            postulacion.setId(new PostulacionId(usuario.getId(), postulacionPojo.getId().getVacanteId()));
            postulacion.setCandidato(modelMapper.map(candidatoServiceImp.obtenerPorId(usuario.getId()), Candidato.class));
            postulacion.setVacante(vacanteServiceImp.obtenerPorId(postulacionPojo.getId().getVacanteId()).get());
            postulacion.setEstadoVacante(estadoVacanteServiceImp.obtenerPorId(1L).get());
            postulacion.setCv(postulacionPojo.getCv());

            postulacion = serviceImp.guardar(postulacion);

            Candidato to = postulacion.getCandidato();
            Vacante vacante = postulacion.getVacante();

            Map<String, Object> model = new HashMap<>();
            MailRequestPojo requestEmail = new MailRequestPojo();

            String titulo = String.format("Confirmación de postulación a la vacante.");

            // Info receiver
            requestEmail.setFrom(emailFrom);
            requestEmail.setSubject(titulo);
            requestEmail.setToEmail(to.getCorreoElectronico());
            requestEmail.setName("PRET SUPPORT");

            // Content
            model.put("titulo", titulo);
            model.put("descripcion",
                    String.format(
                            "<h3>Usted se ha postulado a la siguiente vacante:</h3><br/><br/>" +
                                VACANTE_INFO
                            , vacante.getNombre()
                            , vacante.getTipo()
                            , vacante.getModalidad()
                            , vacante.getPeriodoPago()
                            , vacante.getDescripcion()
                            , String.format("%s/candidato/vacanteDetalle/%d", urlFrontEnd, vacante.getId())
                    )
            );

            if (emailService.sendEmail(requestEmail, model) != null) {
                title = "Envío exitoso";
                message = "La postulación fue exitosa";
                status = HttpStatus.CREATED;
                data = true;
            }
        }

        return new ResponseEntity<>(response.buildStandardResponse(title, data, message), status);
    }

    @Transactional
    @PostMapping("/actualizar")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> actualizar (@RequestHeader HttpHeaders headers, @Valid @RequestBody PostulacionPojo postulacionPojo) {
        title = "Actualizar estado de postulación";
        message = "Error al actualizar el estado de la vacante";
        status = HttpStatus.OK;

        Usuario usuario = jwtTokenFilter.getUserDetails(headers);
        Optional<Postulacion> postulacionDb = serviceImp.obtenerPorId(modelMapper.map(postulacionPojo.getId(), PostulacionId.class));
        Postulacion postulacion = null;

        if (postulacionDb.isPresent())
            postulacion = postulacionDb.get();

        if (postulacion != null && postulacion.getVacante().getReclutador().getId().equals(usuario.getId())) {
            postulacion.setEstadoVacante(modelMapper.map(postulacionPojo.getEstadoVacante(), EstadoVacante.class));
            postulacion = serviceImp.guardar(postulacion);

            Candidato to = postulacion.getCandidato();
            Vacante vacante = postulacion.getVacante();

            Map<String, Object> model = new HashMap<>();
            MailRequestPojo requestEmail = new MailRequestPojo();

            String titulo = String.format("Se ha actualizado el estado de tu vacante.");

            // Info receiver
            requestEmail.setFrom(emailFrom);
            requestEmail.setSubject(titulo);
            requestEmail.setToEmail(to.getCorreoElectronico());
            requestEmail.setName(String.format("%s - %s", vacante.getNombre(), vacante.getReclutador().getNombreEmpresa()));

            // Content
            model.put("titulo", titulo);
            model.put("descripcion",
                    String.format(
                            messages.get(postulacionPojo.getEstadoVacante().getId()) +
                            "<h3>Usted se ha postulado a la siguiente vacante:</h3><br/><br/>" +
                                    VACANTE_INFO
                            , vacante.getNombre()
                            , vacante.getTipo()
                            , vacante.getModalidad()
                            , vacante.getPeriodoPago()
                            , vacante.getDescripcion()
                            , String.format("%s/candidato/vacanteDetalle/%d", urlFrontEnd, vacante.getId())
                    )
            );

            // Cuando el estado de la vacante es contratado, entonces los demás postulantes los marca como rechazados
            if (postulacion.getEstadoVacante().getId().equals(5L))
                sendFailEmail(requestEmail, model, vacante.getPostulaciones(), postulacionPojo, vacante);
            else
                emailService.sendEmail(requestEmail, model);

            title = "Envío exitoso";
            message = "La actualización fue exitosa";
            status = HttpStatus.OK;
            data = true;
        }

        return new ResponseEntity<>(response.buildStandardResponse(title, data, message), status);
    }

    private void sendFailEmail (MailRequestPojo request, Map<String, Object> model, Set<Postulacion> postulaciones, PostulacionPojo postulacionPojo, Vacante vacante) {
        EstadoVacante fail = estadoVacanteServiceImp.obtenerPorId(6L).get();
        for (Postulacion p : postulaciones) {
            if (!p.getEstadoVacante().getId().equals(5L)) {
                p.setEstadoVacante(fail);
                serviceImp.guardar(p);
            }

            request.setToEmail(p.getCandidato().getCorreoElectronico());

            model.put("descripcion",
                    String.format(
                            messages.get(p.getEstadoVacante().getId()) +
                                    "<h3>Usted se ha postulado a la siguiente vacante:</h3><br/>" +
                                    VACANTE_INFO
                            , vacante.getNombre()
                            , vacante.getTipo()
                            , vacante.getModalidad()
                            , vacante.getPeriodoPago()
                            , vacante.getDescripcion()
                            , String.format("%s/candidato/vacanteDetalle/%d", urlFrontEnd, vacante.getId())
                    )
            );

            emailService.sendEmail(request, model);
        }
    }
}
