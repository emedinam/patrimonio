package ec.patrimonio.web.valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ec.patrimonio.compartido.datos.dto.UsuarioDTO;
import ec.patrimonio.compartido.servicios.ServicioUsuario;

@Component
public class ValidadorFormularioUsuario implements Validator{

    private ServicioUsuario servicio;

    @Autowired
    public ValidadorFormularioUsuario(ServicioUsuario servicio) {
        super();
        this.servicio = servicio;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(UsuarioDTO.class);
    }

    @Override
    public void validate(Object objeto, Errors errores) {
        UsuarioDTO dto = (UsuarioDTO) objeto;
        validaPassword(errores,dto);
        validaEmail(errores,dto);
    }

    private void validaPassword(Errors errores, UsuarioDTO dto) {
        if(!dto.getPassword().equals(dto.getConfirmacion())){
            errores.reject("password.no_iguales","Las contraseñas no coinciden");
        }
    }

    private void validaEmail(Errors errores, UsuarioDTO dto) {
        if(servicio.getPorEmail(dto.getEmail()).isPresent()){
            errores.reject("email.existe","La dirección de correo electrónico ya está registrada");
        }
    }

}
