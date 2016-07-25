package ec.patrimonio.web.servicios.impl;

import java.util.Collection;
import javax.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.common.base.Optional;
import ec.patrimonio.compartido.servicios.ServicioArbol;
import ec.patrimonio.compartido.datos.Arbol;
import ec.patrimonio.web.datos.RepositorioArbol;

@Service
public class ServicioArbolImpl implements ServicioArbol {

    private RepositorioArbol repositorio;

    /**
     * @param repositorio
     */
    @Autowired
    public ServicioArbolImpl(RepositorioArbol repositorio) {
        super();
        this.repositorio = repositorio;
    }

    @Override
    public Optional<Arbol> getPorId(Long id) {
        return Optional.fromNullable(repositorio.findOne(id));
    }

    @Override
    public Collection<Arbol> getTodos() {
        return repositorio.findAll();
    }

    @Override
    public Arbol crear(@Nonnull Arbol arbol) {
        return repositorio.save(arbol);
    }

    @Override
    public void eliminar(@Nonnull Arbol arbol) {
        repositorio.delete(arbol);
    }

    @Override
    public void actualizar(@Nonnull Arbol arbol) {
        repositorio.save(arbol);
    }

}
