package ec.patrimonio.web.servicios.impl;

import java.util.Collection;
import javax.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.common.base.Optional;
import ec.patrimonio.compartido.servicios.ServicioFotoSeguimiento;
import ec.patrimonio.compartido.datos.FotoSeguimiento;
import ec.patrimonio.web.datos.RepositorioFotoSeguimiento;

@Service
public class ServicioFotoSeguimientoImpl implements ServicioFotoSeguimiento {

    private RepositorioFotoSeguimiento repositorio;

    /**
     * @param repositorio
     */
    @Autowired
    public ServicioFotoSeguimientoImpl(RepositorioFotoSeguimiento repositorio) {
        super();
        this.repositorio = repositorio;
    }

    @Override
    public Optional<FotoSeguimiento> getPorId(Long id) {
        return Optional.fromNullable(repositorio.findOne(id));
    }

    @Override
    public Collection<FotoSeguimiento> getTodos() {
        return repositorio.findAll();
    }

    @Override
    public FotoSeguimiento crear(@Nonnull FotoSeguimiento fotoSeguimiento) {
        return repositorio.save(fotoSeguimiento);
    }

    @Override
    public void eliminar(@Nonnull FotoSeguimiento fotoSeguimiento) {
        repositorio.delete(fotoSeguimiento);
    }

    @Override
    public void actualizar(@Nonnull FotoSeguimiento fotoSeguimiento) {
        repositorio.save(fotoSeguimiento);
    }

}
