package ec.patrimonio.web.servicios.impl;

import java.util.Collection;
import javax.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.common.base.Optional;
import ec.patrimonio.compartido.servicios.ServicioArbolRegistrado;
import ec.patrimonio.compartido.datos.ArbolRegistrado;
import ec.patrimonio.web.datos.RepositorioArbolRegistrado;

@Service
public class ServicioArbolRegistradoImpl implements ServicioArbolRegistrado {

    private RepositorioArbolRegistrado repositorio;

    /**
     * @param repositorio
     */
    @Autowired
    public ServicioArbolRegistradoImpl(RepositorioArbolRegistrado repositorio) {
        super();
        this.repositorio = repositorio;
    }

    @Override
    public Optional<ArbolRegistrado> getPorId(Long id) {
        return Optional.fromNullable(repositorio.findOne(id));
    }

    @Override
    public Collection<ArbolRegistrado> getTodos() {
        return repositorio.findAll();
    }

    @Override
    public ArbolRegistrado crear(@Nonnull ArbolRegistrado arbolRegistrado) {
        return repositorio.save(arbolRegistrado);
    }

    @Override
    public void eliminar(@Nonnull ArbolRegistrado arbolRegistrado) {
        repositorio.delete(arbolRegistrado);
    }

    @Override
    public void actualizar(@Nonnull ArbolRegistrado arbolRegistrado) {
        repositorio.save(arbolRegistrado);
    }

}
