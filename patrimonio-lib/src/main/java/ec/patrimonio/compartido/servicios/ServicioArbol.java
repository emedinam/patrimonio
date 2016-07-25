package ec.patrimonio.compartido.servicios;
import ec.patrimonio.compartido.datos.Arbol;
import java.util.Collection;
import com.google.common.base.Optional;

public interface ServicioArbol {
    Optional<Arbol> getPorId(Long id);
    Collection<Arbol> getTodos();
    Arbol crear(Arbol arbol);
    void eliminar(Arbol arbol);
    void actualizar(Arbol arbol);
}
