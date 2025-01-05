package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.usuarios.DatosAutenticacionUsuarios;
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

    @PostMapping
    public ResponseEntity autentificarUsuario(@RequestBody @Valid DatosAutenticacionUsuarios datosAutenticacionUsuarios){

        Authentication token = new UsernamePasswordAuthenticationToken(datosAutenticacionUsuarios.login(),
                datosAutenticacionUsuarios.clave());

        authenticationManager.authenticate(token);
        return ResponseEntity.ok().build();
    }
}
