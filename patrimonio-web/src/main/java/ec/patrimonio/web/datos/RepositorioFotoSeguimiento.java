package ec.patrimonio.web.datos;
import org.springframework.data.jpa.repository.JpaRepository;
import com.google.common.base.Optional;
import ec.patrimonio.compartido.datos.FotoSeguimiento;

public interface RepositorioFotoSeguimiento extends JpaRepository<FotoSeguimiento, Long>{
}
