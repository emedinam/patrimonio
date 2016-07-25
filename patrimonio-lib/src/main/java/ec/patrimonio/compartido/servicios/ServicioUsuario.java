package ec.patrimonio.compartido.servicios;

import java.util.Collection;

import ec.patrimonio.compartido.datos.Usuario;
import ec.patrimonio.compartido.datos.dto.UsuarioDTO;


public interface ServicioUsuario {

 /**
	 * 
	 * @param id
	 */
	com.google.common.base.Optional<Usuario> getPorId(Long id);
 /**
	 * 
	 * @param email
	 */
	com.google.common.base.Optional<Usuario> getPorEmail(String email);
 Collection<Usuario> getTodos();
 /**
	 * 
	 * @param dto
	 */
	Usuario crear(UsuarioDTO dto);
 /**
	 * 
	 * @param usuario
	 */
	void eliminar(Usuario usuario);
 /**
	 * 
	 * @param u
	 */
	void actualizar(Usuario u);
}
