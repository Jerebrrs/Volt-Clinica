package med.voll.api.infra.security;

import med.voll.api.domain.usuarios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutentificacionService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Buscando usuario con login: " + username);
        var usuario = usuarioRepository.findByLogin(username);
        if (usuario == null) {
            System.out.println("Usuario no encontrado");
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
    return usuario;
    }
}
