package mx.edu.utez.pret.config;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import mx.edu.utez.pret.model.Usuario;

@Component
public class JwtTokenUtil {
    private static final long EXPIRE_DURATION = 24 * 60 * 60 * 1000; // 24 hour
     
    @Value("${app.jwt.secret}")
    private String secretKey;
     
    public String generateAccessToken(Usuario usuario) {
        return Jwts.builder()
            .setSubject(String.format("%s,%s", usuario.getId(), usuario.getCorreoElectronico()))
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
            .signWith(SignatureAlgorithm.HS512, secretKey)
            .compact();
    }
     
    public boolean validateAccessToken(String token) {
        Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        return true;
    }
     
    public String getSubject(String token) {
        return parseClaims(token).getSubject();
    }
     
    private Claims parseClaims(String token) {
        return Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(token)
            .getBody();
    }
}
