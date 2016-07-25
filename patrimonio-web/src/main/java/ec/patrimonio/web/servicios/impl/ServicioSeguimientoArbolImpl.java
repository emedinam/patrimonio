package ec.patrimonio.web.servicios.impl;

import java.util.Collection;
import javax.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.common.base.Optional;
import ec.patrimonio.compartido.servicios.ServicioSeguimientoArbol;
import ec.patrimonio.compartido.datos.SeguimientoArbol;
import ec.patrimonio.web.datos.RepositorioSeguimientoArbol;

@Service
public class ServicioSeguimientoArbolImpl implements ServicioSeguimientoArbol {

    private RepositorioSeguimientoArbol repositorio;

    /**
     * @param repositorio
     */
    @Autowired
    public ServicioSeguimientoArbolImpl(RepositorioSeguimientoArbol repositorio) {
        super();
        this.repositorio = repositorio;
    }

    @Override
    public Optional<SeguimientoArbol> getPorId(Long id) {
        return Optional.fromNullable(repositorio.findOne(id));
    }

    @Override
    public Collection<SeguimientoArbol> getTodos() {
        return repositorio.findAll();
    }

    @Override
    public SeguimientoArbol crear(@Nonnull SeguimientoArbol seguimientoArbol) {
        return repositorio.save(seguimientoArbol);
    }

    @Override
    public void eliminar(@Nonnull SeguimientoArbol seguimientoArbol) {
        repositorio.delete(seguimientoArbol);
    }

    @Override
    public void actualizar(@Nonnull SeguimientoArbol seguimientoArbol) {
        repositorio.save(seguimientoArbol);
    }

}
