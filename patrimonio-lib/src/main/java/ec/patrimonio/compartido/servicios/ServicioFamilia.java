package ec.patrimonio.compartido.servicios;
import ec.patrimonio.compartido.datos.Familia;
import java.util.Collection;
import com.google.common.base.Optional;

public interface ServicioFamilia {
    Optional<Familia> getPorId(Long id);
    Collection<Familia> getTodos();
    Familia crear(Familia familia);
    void eliminar(Familia familia);
    void actualizar(Familia familia);
}
