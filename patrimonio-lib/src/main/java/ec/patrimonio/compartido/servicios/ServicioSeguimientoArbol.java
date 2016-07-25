package ec.patrimonio.compartido.servicios;
import ec.patrimonio.compartido.datos.SeguimientoArbol;
import java.util.Collection;
import com.google.common.base.Optional;

public interface ServicioSeguimientoArbol {
    Optional<SeguimientoArbol> getPorId(Long id);
    Collection<SeguimientoArbol> getTodos();
    SeguimientoArbol crear(SeguimientoArbol seguimientoArbol);
    void eliminar(SeguimientoArbol seguimientoArbol);
    void actualizar(SeguimientoArbol seguimientoArbol);
}
