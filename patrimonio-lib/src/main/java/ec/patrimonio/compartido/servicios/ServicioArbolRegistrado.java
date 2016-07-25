package ec.patrimonio.compartido.servicios;
import ec.patrimonio.compartido.datos.ArbolRegistrado;
import java.util.Collection;
import com.google.common.base.Optional;

public interface ServicioArbolRegistrado {
    Optional<ArbolRegistrado> getPorId(Long id);
    Collection<ArbolRegistrado> getTodos();
    ArbolRegistrado crear(ArbolRegistrado arbolRegistrado);
    void eliminar(ArbolRegistrado arbolRegistrado);
    void actualizar(ArbolRegistrado arbolRegistrado);
}
