package ec.patrimonio.web.servicios.impl;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.google.common.base.Optional;

import ec.patrimonio.compartido.datos.Usuario;
import ec.patrimonio.compartido.servicios.ServicioUsuario;
import ec.patrimonio.web.seguridad.UsuarioActual;



@Service
public class ServicioDetallesUsuarioImpl implements UserDetailsService {

    private final ServicioUsuario servicioUsuario;


    /**
     * @param servicioUsuario
     */
    @Autowired
    public ServicioDetallesUsuarioImpl(ServicioUsuario servicioUsuario) {
        super();
        this.servicioUsuario = servicioUsuario;
    }

    /**
     * Requerido por <b>spring-security</b>
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Logger
        .getLogger("com.catastros.catastrosweb")
        .info("Accediendo con nombre de usuario "+email);
        Optional<Usuario> usuario = servicioUsuario.getPorEmail(email);
//                .orElseThrow(() ->
//                new UsernameNotFoundException("Usuario no encontrado"));
        if(!usuario.isPresent()){
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
        return new UsuarioActual(usuario.get());
    }

}
