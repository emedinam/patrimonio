package ec.patrimonio.web.servicios.impl;

import java.util.Collection;
import javax.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.common.base.Optional;
import ec.patrimonio.compartido.servicios.ServicioFamilia;
import ec.patrimonio.compartido.datos.Familia;
import ec.patrimonio.web.datos.RepositorioFamilia;

@Service
public class ServicioFamiliaImpl implements ServicioFamilia {

    private RepositorioFamilia repositorio;

    /**
     * @param repositorio
     */
    @Autowired
    public ServicioFamiliaImpl(RepositorioFamilia repositorio) {
        super();
        this.repositorio = repositorio;
    }

    @Override
    public Optional<Familia> getPorId(Long id) {
        return Optional.fromNullable(repositorio.findOne(id));
    }

    @Override
    public Collection<Familia> getTodos() {
        return repositorio.findAll();
    }

    @Override
    public Familia crear(@Nonnull Familia familia) {
        return repositorio.save(familia);
    }

    @Override
    public void eliminar(@Nonnull Familia familia) {
        repositorio.delete(familia);
    }

    @Override
    public void actualizar(@Nonnull Familia familia) {
        repositorio.save(familia);
    }

}
