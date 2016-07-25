package ec.patrimonio.compartido.datos.dto;


import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

import ec.patrimonio.compartido.datos.Rol;
import ec.patrimonio.compartido.datos.Usuario;


public class UsuarioDTO {

    @NotEmpty
    private String email = "";

    @NotEmpty
    private String password = "";

    @NotEmpty
    private String confirmacion = "";

    @NotNull
    private Rol rol = ec.patrimonio.compartido.datos.Rol.USUARIO;

    public UsuarioDTO(){

    }

    /**
	 * 
	 * @param usuario
	 */
	public UsuarioDTO(Usuario usuario){
     this.email = usuario.getEmail();
     this.rol = usuario.getRol();
    }
    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmacion() {
        return confirmacion;
    }

    public void setConfirmacion(String confirmacion) {
        this.confirmacion = confirmacion;
    }

    /**
     * @return the rol
     */
    public Rol getRol() {
        return rol;
    }

    /**
     * @param rol the rol to set
     */
    public void setRol(Rol rol) {
        this.rol = rol;
    }

}
