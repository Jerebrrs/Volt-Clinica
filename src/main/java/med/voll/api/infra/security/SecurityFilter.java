package med.voll.api.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.usuarios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

      /* String requestURI = request.getRequestURI();
        if ("/login".equals(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }

        var token = request.getHeader("Authorization");

        if (token == null || !token.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("El token enviado no es válido.");
            return;
        }

        token = token.replace("Bearer ", "");

        try {
            String subject = tokenService.getSubject(token);
            System.out.println("Token válido para el usuario: " + subject);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("El token enviado no es válido.");
            return;
        }*/

        var authHeader = request.getHeader("Authorization");

        if(authHeader != null){

            var token = authHeader.replace("Bearer ", "");
            var subject =tokenService.getSubject(token);
            if(subject != null){
                var usuario = usuarioRepository.findByLogin(subject);
                var authentication= new UsernamePasswordAuthenticationToken(usuario,null,usuario.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}
