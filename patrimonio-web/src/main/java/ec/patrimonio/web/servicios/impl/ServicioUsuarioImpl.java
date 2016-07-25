package ec.patrimonio.web.servicios.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.common.base.Optional;

import ec.patrimonio.compartido.datos.Usuario;
import ec.patrimonio.compartido.datos.dto.UsuarioDTO;
import ec.patrimonio.compartido.servicios.ServicioUsuario;
import ec.patrimonio.web.datos.RepositorioUsuarios;


@Service
public class ServicioUsuarioImpl implements ServicioUsuario{

    private RepositorioUsuarios repositorio;

    @Autowired
    public ServicioUsuarioImpl(RepositorioUsuarios repositorio){
        this.repositorio = repositorio;
    }

    @Override
    public Optional<Usuario> getPorId(Long id) {
        return Optional.fromNullable(repositorio.findOne(id));
    }

    @Override
    public Optional<Usuario> getPorEmail(String email) {
        return repositorio.findByEmail(email);
    }

    @Override
    public Collection<Usuario> getTodos() {
        return repositorio.findAll(new Sort("email"));
    }

    @Override
    public Usuario crear(UsuarioDTO dto) {
        Usuario usuario =
                new Usuario();
        usuario.setEmail(dto.getEmail());
        usuario.setRol(dto.getRol());
        usuario.setPasswordHash(new BCryptPasswordEncoder().encode(dto.getPassword()));
        return repositorio.save(usuario);
    }

	@Override
	public void eliminar(Usuario usuario) {
		repositorio.delete(usuario);
	}

	@Override
	public void actualizar(Usuario u) {
		repositorio.save(u);
	}
}
