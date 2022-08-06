package mx.edu.utez.pret.controller;

import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.edu.utez.pret.config.JwtTokenFilter;
import mx.edu.utez.pret.config.JwtTokenUtil;
import mx.edu.utez.pret.config.Response;
import mx.edu.utez.pret.model.Usuario;
import mx.edu.utez.pret.pojo.AuthRequestPojo;
import mx.edu.utez.pret.pojo.AuthResponsePojo;
import mx.edu.utez.pret.repository.UsuarioRepository;
import mx.edu.utez.pret.pojo.AuthChangePasswordPojo;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired 
    private AuthenticationManager authManager;
    
    @Autowired 
    private JwtTokenUtil jwtUtil;
    
    @Autowired 
    private JwtTokenFilter jwtTokenFilter;

    @Autowired
    private Response response;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody @Valid AuthRequestPojo request) {
        Authentication authentication = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getCorreoElectronico(), request.getContrasena())
        );

        Usuario usuario = (Usuario) authentication.getPrincipal();

        String accessToken = jwtUtil.generateAccessToken(usuario);

        AuthResponsePojo responsePojo = new AuthResponsePojo(usuario.getCorreoElectronico(), accessToken);
            
        return new ResponseEntity<>(response.buildStandardResponse("Autenticación exitosa", responsePojo), HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/change-password")
    public ResponseEntity<Map<String, Object>> changePassword(@RequestHeader HttpHeaders headers, @RequestBody @Valid AuthChangePasswordPojo request) {
        String title = "Cambiar Contraseña";
        String message = "Error al cambiar la contraseña";

        Usuario uDb = null;
        Usuario usuario = jwtTokenFilter.getUserDetails(headers);
        Optional<Usuario> usuarioDb = usuarioRepository.findById(usuario.getId());

        if (usuarioDb.isPresent())
            uDb = usuarioDb.get();
        
        if (uDb != null && passwordEncoder.matches(request.getContrasena(),  uDb.getContrasena()) && request.getNuevaContrasena().equals(request.getRepetirContrasena())) {
            
            uDb.setContrasena(passwordEncoder.encode(request.getNuevaContrasena()));
            usuarioRepository.save(uDb);

            message = "Se ha actualizado la contraseña exitosamente";
        }
            
        return new ResponseEntity<>(response.buildStandardResponse(title, null, message), HttpStatus.OK);
    }
}
