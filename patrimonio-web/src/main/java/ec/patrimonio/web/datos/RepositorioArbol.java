package ec.patrimonio.web.datos;
import org.springframework.data.jpa.repository.JpaRepository;
import com.google.common.base.Optional;
import ec.patrimonio.compartido.datos.Arbol;

public interface RepositorioArbol extends JpaRepository<Arbol, Long>{

}
