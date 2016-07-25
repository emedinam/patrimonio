package ec.patrimonio.web.api;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Function;
import com.google.common.base.Optional;

import ec.patrimonio.compartido.datos.Arbol;
import ec.patrimonio.compartido.datos.ArbolRegistrado;
import ec.patrimonio.compartido.servicios.ServicioArbol;
import ec.patrimonio.compartido.servicios.ServicioArbolRegistrado;
import ec.patrimonio.web.dto.ArbolDTO;
import ec.patrimonio.web.dto.ArbolRegistradoDTO;

@RestController
@RequestMapping("/api/tree/{id}/reg")
public class ControladorArbolRegistrado {

	private ServicioArbol servicio;
	private ServicioArbolRegistrado servReg;
	
	@Autowired
	public ControladorArbolRegistrado(ServicioArbol servicio, ServicioArbolRegistrado servReg) {
		this.servicio = servicio;
		this.servReg = servReg;
	}
	@ExceptionHandler(DataIntegrityViolationException.class)
    ResponseEntity<String> errorEnDatos(DataIntegrityViolationException ex){
        return new ResponseEntity<String>(ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(NoSuchElementException.class)
    ResponseEntity<String> noSeEncuentra(NoSuchElementException ex){
        return new ResponseEntity<String>(ex.getLocalizedMessage(), HttpStatus.NOT_FOUND);
    }

	@RequestMapping(method=GET)
	public ArbolRegistradoDTO getUno(@PathVariable Long id){
		Optional<Arbol> ar = servicio.getPorId(id);
		if(ar.isPresent()){
			return new ArbolRegistradoDTO(ar.get().getRegistro());
		}
		else {
			throw new NoSuchElementException();
		}
	}
	@RequestMapping(method=PUT)
	public void actualizarArbol(@PathVariable Long id,@RequestBody ArbolRegistradoDTO arbol){
		Optional<Arbol> actual = servicio.getPorId(id);
		if(actual.isPresent()){
		// Hacer los Id's actualizables trae su propio conjunto de problemas
		 ArbolRegistrado entidad = actual.get().getRegistro();
		 ArbolRegistrado nuevo = arbol.toArbolRegistrado();
		 entidad.setCedula(nuevo.getCedula());
		 entidad.setFoto(nuevo.getFoto());
		 entidad.setObservaciones(nuevo.getObservaciones());
		 entidad.setSeguimientos(nuevo.getSeguimientos());
		 entidad.setTipo(nuevo.getTipo());
		 servReg.actualizar(entidad);
		}
		else {
			throw new NoSuchElementException();
		}
	}
	
	@RequestMapping(method=POST)
	public ArbolRegistradoDTO crearArbol(@PathVariable Long id,@RequestBody ArbolRegistradoDTO dto){
		Optional<Arbol> arbol = servicio.getPorId(id);
		if(arbol.isPresent()){
		ArbolRegistrado nuevo = dto.toArbolRegistrado();
		nuevo.setArbol(arbol.get());
		servReg.crear(nuevo);
		arbol.get().setRegistro(nuevo);
		return new ArbolRegistradoDTO(nuevo);
		}
		else {
			throw new NoSuchElementException("El arbol no existe para registro");
		}
	}
	@RequestMapping(method=DELETE)
	public void eliminarArbolRegistradro(@PathVariable Long id){
		Optional<Arbol> arbolRegistrado = servicio.getPorId(id);
		if(arbolRegistrado.isPresent()){
			servicio.eliminar(arbolRegistrado.get());
		}
		else {
			throw new NoSuchElementException();
		}
	}
}
