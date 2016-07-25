package ec.patrimonio.web.datos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.google.common.base.Optional;

import ec.patrimonio.compartido.datos.Usuario;

public interface RepositorioUsuarios extends JpaRepository<Usuario, Long>{
    Optional<Usuario> findByEmail(String email);
}
