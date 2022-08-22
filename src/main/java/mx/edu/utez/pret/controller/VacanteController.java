package mx.edu.utez.pret.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
import mx.edu.utez.pret.model.Contacto;
import mx.edu.utez.pret.model.ContactoId;
import mx.edu.utez.pret.model.Reclutador;
import mx.edu.utez.pret.model.Usuario;
import mx.edu.utez.pret.model.Vacante;
import mx.edu.utez.pret.pojo.MailRequestPojo;
import mx.edu.utez.pret.pojo.MailResponsePojo;
import mx.edu.utez.pret.pojo.ReclutadorPojo;
import mx.edu.utez.pret.pojo.VacantePojo;
import mx.edu.utez.pret.service.ContactoServiceImp;
import mx.edu.utez.pret.service.EmailService;
import mx.edu.utez.pret.service.ReclutadorServiceImp;
import mx.edu.utez.pret.service.VacanteServiceImp;

@RestController
@RequestMapping("/vacante")
public class VacanteController {

    @Value("${app.front-end}")
    private String urlFrontEnd;
    
    @Autowired
    private VacanteServiceImp serviceImp;

    @Autowired
    private ReclutadorServiceImp reclutadorServiceImp;

    @Autowired
    private ContactoServiceImp contactoServiceImp;

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

    @GetMapping("/obtener")
    public ResponseEntity<Map<String, Object>> obtenerTodos () {
        return new ResponseEntity<>(response.buildStandardResponse("Obtener todas las vacantes", modelMapper.mapList(serviceImp.obtenerTodos(LocalDate.now()), VacantePojo.class)), HttpStatus.OK);
    }

    @GetMapping("/obtener/{id}")
    public ResponseEntity<Map<String, Object>> obtenerPorId (@PathVariable Optional<Long> id) {
        String title = "Obtener una vacante";
        Long idL = id.isPresent() ? id.get() : null;
        Optional<Vacante> vacante = serviceImp.obtenerPorId(idL);
        Map<String, Object> res = vacante.isPresent() 
            ? response.buildStandardResponse(title, modelMapper.map(vacante.get(), VacantePojo.class))
            : response.buildStandardResponse(title, null, String.format("El vacante con el id %d no existe", idL));
        
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/registrar")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> registrar (@RequestHeader HttpHeaders headers, @Valid @RequestBody VacantePojo vacantePojo) {
        title = "Registrar vacante";
        message = "Error al guardar el vacante";
        VacantePojo answ = null;
        status = HttpStatus.OK;

        Usuario usuario = jwtTokenFilter.getUserDetails(headers);
        Optional<Reclutador> reclutadorDb = reclutadorServiceImp.obtenerPorId(usuario.getId());

        if (reclutadorDb.isPresent()) {
            vacantePojo.setReclutador(modelMapper.map(reclutadorDb.get(), ReclutadorPojo.class));
            Vacante vacante = modelMapper.map(vacantePojo, Vacante.class);
            vacante = serviceImp.guardar(vacante);
    
            if (vacante != null && vacante.getId() != null) {
                answ = modelMapper.map(vacante, VacantePojo.class);
                answ.getReclutador().setVacantes(null);
                title = "Vacante registrado";
                message = "La vacante ha sido registrado satisfactoriamente";
                status = HttpStatus.CREATED;
            }
        }

        return new ResponseEntity<>(response.buildStandardResponse(title, answ, message), status);
    }

    @Transactional
    @PostMapping("/actualizar")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> actualizar (@RequestHeader HttpHeaders headers, @Valid @RequestBody VacantePojo vacantePojo) {
        title = "Actualizar vacante";
        message = "No se realizo la actualización";
        VacantePojo answ = null;
        Reclutador reclutador = null;
        Vacante vacante = null;

        Usuario usuario = jwtTokenFilter.getUserDetails(headers);
        Optional<Reclutador> reclutadorDb = reclutadorServiceImp.obtenerPorId(usuario.getId());
        Optional<Vacante> vacanteDb = serviceImp.obtenerPorId(vacantePojo.getId());
        
        if (reclutadorDb.isPresent() && vacanteDb.isPresent()) {
            vacante = vacanteDb.get();
            reclutador = reclutadorDb.get();
            
            // Para actualizar la vacante, es necesario que la vacante pertenezca al reclutador.
            if (vacante.getReclutador().getId().equals(reclutador.getId())) {
                vacante.setNombre(vacantePojo.getNombre());   
                vacante.setDescripcion(vacantePojo.getDescripcion());   
                vacante.setModalidad(vacantePojo.getModalidad());   
                vacante.setTipo(vacantePojo.getTipo());   
                vacante.setFechaInicio(vacantePojo.getFechaInicio());   
                vacante.setSueldoMin(vacantePojo.getSueldoMin());   
                vacante.setSueldoMax(vacantePojo.getSueldoMax());   
                vacante.setPeriodoPago(vacantePojo.getPeriodoPago());   
                vacante.setFechaVigencia(vacantePojo.getFechaVigencia());   
                vacante = serviceImp.guardar(vacante);
    
                title = "Vacante actualizado";
                message = "Actualización exitosa";
                
                answ = modelMapper.map(vacante, VacantePojo.class);
            }
        }


        return new ResponseEntity<>(response.buildStandardResponse(title, answ, message), HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/eliminar/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> eliminar (@RequestHeader HttpHeaders headers, @PathVariable Optional<Long> id) {
        title = "Eliminar vacante";
        message = "Ninguna vacante fue eliminada";
        Long idL = id.isPresent() ? id.get() : null;
        data = false;

        Reclutador reclutador = null;
        Vacante vacante = null;
        Usuario usuario = jwtTokenFilter.getUserDetails(headers);
        Optional<Reclutador> reclutadorDb = reclutadorServiceImp.obtenerPorId(usuario.getId());
        Optional<Vacante> vacanteDb = serviceImp.obtenerPorId(idL);
        
        if (reclutadorDb.isPresent() && vacanteDb.isPresent()) {
            vacante = vacanteDb.get();
            reclutador = reclutadorDb.get();

            // Para eliminar la vacante, es necesario que la vacante pertenezca al reclutador.
            if (vacante.getReclutador().getId().equals(reclutador.getId()) && serviceImp.eliminar(idL)) {
                data = true;
                title = "Vacante eliminada exitosamente";
                message = "La vacante ha sido eliminada";
            }
        }
        
        return new ResponseEntity<>(response.buildStandardResponse(title, data, message), HttpStatus.OK);
    }

    @GetMapping("/obtener/reclutador")
    public ResponseEntity<Map<String, Object>> obtenerPorReclutador (@RequestHeader HttpHeaders headers) {
        Usuario usuario = jwtTokenFilter.getUserDetails(headers);
        Optional<Reclutador> reclutadorDb = reclutadorServiceImp.obtenerPorId(usuario.getId());
        Reclutador reclutador = reclutadorDb.isPresent() ? reclutadorDb.get() : null;
        return new ResponseEntity<>(response.buildStandardResponse("Obtener las vacantes de un reclutador", modelMapper.mapList(serviceImp.obtenerPorReclutador(reclutador), VacantePojo.class)), HttpStatus.OK);
    }
    
    @GetMapping("/compartir/{idVacante}/{idUsuario}")
    public ResponseEntity<Map<String, Object>> compartir (@RequestHeader HttpHeaders headers, @PathVariable Optional<Long> idVacante, @PathVariable Optional<Long> idUsuario) {  
        title = "Compartir una vacante";
        message = "Ninguna vacante fue compartida";
        MailResponsePojo responseEmail = null; 

        Usuario usuario = jwtTokenFilter.getUserDetails(headers);
        ContactoId id1 = new ContactoId(usuario.getId(), idUsuario.isPresent() ? idUsuario.get() : null);
        ContactoId id2 = new ContactoId(idUsuario.isPresent() ? idUsuario.get() : null, usuario.getId());
        Optional<Contacto> contacto1Db = contactoServiceImp.obtenerPorId(id1);
        Optional<Contacto> contacto2Db = contactoServiceImp.obtenerPorId(id2);
        Optional<Vacante> vacanteDb = serviceImp.obtenerPorId(idVacante.isPresent() ? idVacante.get() : null);
        Contacto contacto = null;

        if (contacto1Db.isPresent())
            contacto = contacto1Db.get();
        else if (contacto2Db.isPresent())
            contacto = contacto2Db.get();

        // Verifica que ambos sean contactos y que exista la vacante
        if (contacto != null && vacanteDb.isPresent()) {
            Vacante vacante = vacanteDb.get();

            // Manda el correo si ambos son contactos vigentes
            if (contacto.getEstado()) {
                Candidato from;
                Candidato to;
                if (contacto.getCandidato().getId().equals(usuario.getId())) {
                    from = contacto.getCandidato();
                    to = contacto.getAmigo();
                } else {
                    to = contacto.getCandidato();
                    from = contacto.getAmigo();
                }

                Map<String, Object> model = new HashMap<>();
                MailRequestPojo requestEmail = new MailRequestPojo();

                String nombre = String.format("%s %s", from.getNombre(), from.getApellidoPaterno());
                String titulo = String.format("Tu amigo %s ha compartido esta vacante contigo.", nombre);

                // Info receiver
                requestEmail.setFrom(from.getCorreoElectronico());
                requestEmail.setSubject(titulo);
                requestEmail.setToEmail(to.getCorreoElectronico());
                requestEmail.setName(nombre);

                // Content
                model.put("backgroundColor", "blue");
                model.put("titulo", titulo);
                model.put("descripcion",
                    String.format(
                        "<h3>Datos de la vacante</h3><br/><br/>" +
                        "<b>Nombre:</b> %s <br />" +
                        "<b>Tipo:</b> %s <br />" +
                        "<b>Modalidad:</b> %s <br />" +
                        "<b>Periodo de pago:</b> %s <br />" +
                        "<b>Descripción:</b> <br /> <p>%s</p> <br /> <br />" +
                        "<h3><a href=\"%s\">Haz clic aquí para visitar la vacante</a></h3> <br /> "
                        , vacante.getNombre()
                        , vacante.getTipo()
                        , vacante.getModalidad()
                        , vacante.getPeriodoPago()
                        , vacante.getDescripcion()
                        , String.format("%s/candidato/vacanteDetalle/%d", urlFrontEnd, vacante.getId())
                    )
                );

                responseEmail = emailService.sendEmail(requestEmail, model);

                if (responseEmail != null)
                    message = "Se ha compartido la vacante";
            }
        }
        
        return new ResponseEntity<>(response.buildStandardResponse(title, responseEmail, message), HttpStatus.OK);
    }
}
