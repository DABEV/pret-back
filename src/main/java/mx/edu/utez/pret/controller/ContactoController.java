package mx.edu.utez.pret.controller;

import mx.edu.utez.pret.config.CustomModelMapper;
import mx.edu.utez.pret.config.JwtTokenFilter;
import mx.edu.utez.pret.config.Response;
import mx.edu.utez.pret.model.*;
import mx.edu.utez.pret.pojo.*;
import mx.edu.utez.pret.service.CandidatoServiceImp;
import mx.edu.utez.pret.service.ContactoServiceImp;
import mx.edu.utez.pret.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/contacto")
public class ContactoController {

    @Value("${app.front-end}")
    private String urlFrontEnd;

    @Autowired
    private ContactoServiceImp serviceImp;

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

    @GetMapping("/lista-amigos")
    public ResponseEntity<Map<String, Object>> listaAmigos (@RequestHeader HttpHeaders headers) {
        title = "Lista de amigos";
        List<ContactoPojo> answ = null;
        Usuario usuario = jwtTokenFilter.getUserDetails(headers);
        Optional<Candidato> candidatoDb = candidatoServiceImp.obtenerPorId(usuario.getId());

        if (candidatoDb.isPresent()) {
            Candidato candidato = candidatoDb.get();
            answ = Stream.of(
                    modelMapper.mapList(serviceImp.obtenerPorCandidato(candidato), ContactoPojo.class),
                    modelMapper.mapList(serviceImp.obtenerPorAmigo(candidato), ContactoPojo.class))
                    .flatMap(x -> x.stream())
                    .collect(Collectors.toList());
        }

        return new ResponseEntity<>(response.buildStandardResponse(title, answ, message), HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/enviar-solicitud")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> registrar (@RequestHeader HttpHeaders headers, @Valid @RequestBody ContactoPojo contactoPojo) {
        title = "Enviar solicitud de amistad";
        message = "Error al enviar la solicitud";
        data = false;
        status = HttpStatus.OK;

        Usuario usuario = jwtTokenFilter.getUserDetails(headers);
        Optional<Candidato> candidatoDb = candidatoServiceImp.obtenerPorId(usuario.getId());

        if (candidatoDb.isPresent()) {
            ContactoId id1 = new ContactoId(usuario.getId(), contactoPojo.getId().getAmigoId());
            ContactoId id2 = new ContactoId(contactoPojo.getId().getAmigoId(), usuario.getId());

            // Verifica si ya existe una solicitud existente
            Optional<Contacto> try1 = serviceImp.obtenerPorId(id1);
            Optional<Contacto> try2 = serviceImp.obtenerPorId(id2);

            if (!try1.isPresent() && !try2.isPresent()) {
                Contacto contacto = new Contacto();
                contacto.setId(id1);
                contacto.setCandidato(candidatoServiceImp.obtenerPorId(id1.getCandidatoId()).get());
                contacto.setAmigo(candidatoServiceImp.obtenerPorId(id1.getAmigoId()).get());
                contacto.setEstado(false);
                contacto = serviceImp.guardar(contacto);

                Candidato from = contacto.getCandidato();
                Candidato to = contacto.getAmigo();

                Map<String, Object> model = new HashMap<>();
                MailRequestPojo requestEmail = new MailRequestPojo();

                String nombre = String.format("%s %s", from.getNombre(), from.getApellidoPaterno());
                String titulo = String.format("Tienes una nueva solicitud de amistad de %s.", nombre);

                // Info receiver
                requestEmail.setFrom(from.getCorreoElectronico());
                requestEmail.setSubject(titulo);
                requestEmail.setToEmail(to.getCorreoElectronico());
                requestEmail.setName(nombre);

                // Content
                model.put("titulo", titulo);
                model.put("descripcion",
                        String.format(
                                "<h3>%s quiere ser tu amigo(a)</h3><br/><br/>" +
                                "<b>Email:</b> %s <br />" +
                                "<b>Telefono:</b> %s <br />" +
                                "<b>Estado de residencia:</b> %s <br />"
                                // "<h3><a href=\"%s\">Haz clic aquí para visitar su perfil</a></h3> <br /> " +
                                + ""
                                , nombre
                                , from.getCorreoElectronico()
                                , from.getTelefono()
                                , from.getEstadoRepublica() != null ? from.getEstadoRepublica().getNombre() : "-"
                                // , String.format("%s/candidato/perfil/%d", urlFrontEnd, from.getId())
                        )
                );

                if (emailService.sendEmail(requestEmail, model) != null) {
                    title = "Envío exitoso";
                    message = "La solicitud fue enviada exitosamente";
                    status = HttpStatus.CREATED;
                    data = true;
                }
            } else {
                message = "Ya son amigos o la solicitud esta pendiente";
            }
        }

        return new ResponseEntity<>(response.buildStandardResponse(title, data, message), status);
    }

    @GetMapping("/aceptar/{id}")
    public ResponseEntity<Map<String, Object>> aceptar (@RequestHeader HttpHeaders headers, @PathVariable Optional<Long> id) {
        title = "Aceptar solicitud de amistad";
        message = "Error al aceptar la solicitud";
        data = false;
        status = HttpStatus.OK;

        Usuario usuario = jwtTokenFilter.getUserDetails(headers);
        Optional<Candidato> candidatoDb = candidatoServiceImp.obtenerPorId(usuario.getId());

        if (candidatoDb.isPresent()) {
            ContactoId id1 = new ContactoId(id.get(), usuario.getId());

            // Verifica si ya existe una solicitud existente
            Optional<Contacto> try1 = serviceImp.obtenerPorId(id1);

            if (try1.isPresent()) {
                Contacto contacto = try1.get();
                contacto.setEstado(true);
                contacto = serviceImp.guardar(contacto);

                Candidato to = contacto.getCandidato();
                Candidato from = contacto.getAmigo();

                Map<String, Object> model = new HashMap<>();
                MailRequestPojo requestEmail = new MailRequestPojo();

                String nombre = String.format("%s %s", from.getNombre(), from.getApellidoPaterno());
                String titulo = String.format("Tu y %s ahora son amigos.", nombre);

                // Info receiver
                requestEmail.setFrom(from.getCorreoElectronico());
                requestEmail.setSubject(titulo);
                requestEmail.setToEmail(to.getCorreoElectronico());
                requestEmail.setName(nombre);

                // Content
                model.put("titulo", titulo);
                model.put("descripcion",
                        String.format(
                            "<h3>Ve a iniciar sesión para ver el perfil de tu amigo</h3><br/><br/>"
                        )
                );

                if (emailService.sendEmail(requestEmail, model) != null) {
                    title = "Solicitud aceptada";
                    message = "La solicitud fue aceptada, ahora son amigos";
                    data = true;
                }
            } else {
                message = "La solicitud no existe";
            }
        }

        return new ResponseEntity<>(response.buildStandardResponse(title, data, message), status);
    }

    @DeleteMapping("/rechazar/{id}")
    public ResponseEntity<Map<String, Object>> rechazar (@RequestHeader HttpHeaders headers, @PathVariable Optional<Long> id) {
        title = "Rechazar solicitud de amistad";
        message = "Error al rechazar la solicitud";
        data = false;
        status = HttpStatus.OK;

        Usuario usuario = jwtTokenFilter.getUserDetails(headers);
        Optional<Candidato> candidatoDb = candidatoServiceImp.obtenerPorId(usuario.getId());

        if (candidatoDb.isPresent()) {
            ContactoId id1 = new ContactoId(id.get(), usuario.getId());

            // Verifica si ya existe una solicitud existente
            Optional<Contacto> try1 = serviceImp.obtenerPorId(id1);

            if (try1.isPresent()) {
                serviceImp.eliminar(id1);
                title = "Solicitud eliminada";
                message = "La solicitud fue eliminada";
                data = true;
            } else {
                message = "La solicitud no existe";
            }
        }

        return new ResponseEntity<>(response.buildStandardResponse(title, data, message), status);
    }
}
