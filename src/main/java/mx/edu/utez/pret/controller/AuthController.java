package mx.edu.utez.pret.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.edu.utez.pret.config.JwtTokenUtil;
import mx.edu.utez.pret.config.Response;
import mx.edu.utez.pret.model.Usuario;
import mx.edu.utez.pret.pojo.AuthRequestPojo;
import mx.edu.utez.pret.pojo.AuthResponsePojo;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired 
    private AuthenticationManager authManager;
    
    @Autowired 
    private JwtTokenUtil jwtUtil;

    @Autowired
    private Response response;

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
}
