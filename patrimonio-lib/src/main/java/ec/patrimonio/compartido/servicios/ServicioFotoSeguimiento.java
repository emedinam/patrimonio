package ec.patrimonio.compartido.servicios;
import ec.patrimonio.compartido.datos.FotoSeguimiento;
import java.util.Collection;
import com.google.common.base.Optional;

public interface ServicioFotoSeguimiento {
    Optional<FotoSeguimiento> getPorId(Long id);
    Collection<FotoSeguimiento> getTodos();
    FotoSeguimiento crear(FotoSeguimiento fotoSeguimiento);
    void eliminar(FotoSeguimiento fotoSeguimiento);
    void actualizar(FotoSeguimiento fotoSeguimiento);
}
