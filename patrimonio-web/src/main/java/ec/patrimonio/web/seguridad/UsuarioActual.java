package ec.patrimonio.web.seguridad;

import org.springframework.security.core.authority.AuthorityUtils;

import ec.patrimonio.compartido.datos.Rol;
import ec.patrimonio.compartido.datos.Usuario;
// TODO Esta clase deberia estar en otro namespace

public class UsuarioActual extends org.springframework.security.core.userdetails.User {

    // Esta clase es serializable
    private static final long serialVersionUID = -2216121670894934857L;

    private Usuario usuario;

    public UsuarioActual(Usuario usuario){
        super(usuario.getEmail()
                , usuario.getPasswordHash()
                , AuthorityUtils.createAuthorityList(usuario.getRol().toString()));
        this.usuario = usuario;
    }

    public Long getId(){
        return usuario.getId();
    }

    public Usuario getUsuario(){
        return usuario;
    }

    public Rol getRol(){
        return usuario.getRol();
    }

    /* Requerido por <b>spring-security</b>
     * @see org.springframework.security.core.userdetails.User#getPassword()
     */
    @Override
    public String getPassword() {
        return usuario.getPasswordHash();
    }

    /* Requerido por <b>spring-security</b>
     * @see org.springframework.security.core.userdetails.User#getUsername()
     */
    @Override
    public String getUsername() {
        return usuario.getEmail();
    }
}
