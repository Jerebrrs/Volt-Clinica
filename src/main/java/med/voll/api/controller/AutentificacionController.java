package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.usuarios.DatosAutenticacionUsuarios;
import med.voll.api.domain.usuarios.Usuario;
import med.voll.api.infra.security.DatosJwtRoken;
import med.voll.api.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutentificacionController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity autentificarUsuario(@RequestBody @Valid DatosAutenticacionUsuarios datosAutenticacionUsuarios){

        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(datosAutenticacionUsuarios.login(),
                datosAutenticacionUsuarios.clave());

        authenticationManager.authenticate(authenticationToken);

        var usuarioAuthenticado = authenticationManager.authenticate(authenticationToken);

        var jwtToken = tokenService.generarToken((Usuario) usuarioAuthenticado.getPrincipal());
        return ResponseEntity.ok(new DatosJwtRoken(jwtToken));
    }

}
