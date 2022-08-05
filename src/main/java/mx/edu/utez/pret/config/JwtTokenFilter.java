package mx.edu.utez.pret.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import mx.edu.utez.pret.model.Usuario;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenUtil jwtUtil;
 
    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
 
        if (!hasAuthorizationBearer(request)) {
            filterChain.doFilter(request, response);
            return;
        }
 
        String token = getAccessToken(request);
 
        if (!jwtUtil.validateAccessToken(token)) {
            filterChain.doFilter(request, response);
            return;
        }
 
        setAuthenticationContext(token, request);
        filterChain.doFilter(request, response);
    }
 
    private boolean hasAuthorizationBearer(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        return !(ObjectUtils.isEmpty(header) || !header.startsWith("Bearer"));
    }
 
    private String getAccessToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        return header.substring("Bearer ".length());
    }
    
    private void setAuthenticationContext(String token, HttpServletRequest request) {
        UserDetails userDetails = getUserDetails(token);
        
        UsernamePasswordAuthenticationToken
        authentication = new UsernamePasswordAuthenticationToken(userDetails, null, null);
 
        authentication.setDetails(
            new WebAuthenticationDetailsSource().buildDetails(request)
        );
            
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public String getAccessToken(HttpHeaders headers) {
        String token = headers.getFirst(HttpHeaders.AUTHORIZATION);
        return token.substring("Bearer ".length());
    }
        
    /**
     * Get User info from token
     */
    public UserDetails getUserDetails(String token) {
        Usuario userDetails = new Usuario();
        String[] jwtSubject = jwtUtil.getSubject(token).split(",");
 
        userDetails.setId(Long.parseLong(jwtSubject[0]));
        userDetails.setCorreoElectronico(jwtSubject[1]);
 
        return userDetails;
    }

    /**
     * Get User info from token
     */
    public Usuario getUserDetails(HttpHeaders headers) {
        return (Usuario) getUserDetails(getAccessToken(headers));
    }
}
