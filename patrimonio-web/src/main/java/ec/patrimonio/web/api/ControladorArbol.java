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
import ec.patrimonio.compartido.servicios.ServicioArbol;
import ec.patrimonio.web.dto.ArbolDTO;

@RestController
@RequestMapping("/api/tree")
public class ControladorArbol {

	private ServicioArbol servicio;
	private Function<Collection<Arbol>, Collection<ArbolDTO>> arbol2DTO;
	
	@Autowired
	public ControladorArbol(ServicioArbol servicio) {
		super();
		this.servicio = servicio;
		arbol2DTO = new Function<Collection<Arbol>, Collection<ArbolDTO>>() {
			
			@Override
			public Collection<ArbolDTO> apply(Collection<Arbol> arboles) {
				Collection<ArbolDTO> dtos = new ArrayList<ArbolDTO>();
				for(Arbol a: arboles){
					dtos.add(new ArbolDTO(a));
				}
				return dtos;
			}
		};
	}
	
	@ExceptionHandler(NoSuchElementException.class)
    ResponseEntity<String> noSeEncuentra(NoSuchElementException ex){
        return new ResponseEntity<String>(ex.getLocalizedMessage(), HttpStatus.NOT_FOUND);
    }
	
	@ExceptionHandler(DataIntegrityViolationException.class)
    ResponseEntity<String> errorEnDatos(DataIntegrityViolationException ex){
        return new ResponseEntity<String>(ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
    }
	
	@RequestMapping(method=GET)
	public Collection<ArbolDTO> getTodos(){
		Collection<Arbol> arboles = servicio.getTodos();
		return arbol2DTO.apply(arboles);
	}
	
	@RequestMapping(method=GET,value="/{id}")
	public ArbolDTO getArbol(@PathVariable Long id){
		Optional<Arbol> arbol = servicio.getPorId(id);
		if(arbol.isPresent()){
			return new ArbolDTO(arbol.get());
		}
		else {
			throw new NoSuchElementException();
		}
	}
	
	@RequestMapping(method=POST)
	public ArbolDTO crearArbol(@RequestBody ArbolDTO arbol){
		Arbol nuevo = arbol.toArbol();
		servicio.crear(nuevo);
		return new ArbolDTO(nuevo);
	}
	
	@RequestMapping(method=PUT,value="/{id}")
	public void actualizarArbol(@RequestBody ArbolDTO arbol){
		Optional<Arbol> actual = servicio.getPorId(arbol.getId());
		if(actual.isPresent()){
		// Hacer los Id's actualizables trae su propio conjunto de problemas
		 Arbol entidad = actual.get();
		 Arbol nuevo = arbol.toArbol();
		 entidad.setContenidoInformativo(nuevo.getContenidoInformativo());
		 entidad.setFamilia(nuevo.getFamilia());
		 entidad.setNombreCientifico(nuevo.getNombreCientifico());
		 entidad.setNombreComun(nuevo.getNombreComun());
		 servicio.actualizar(entidad);
		}
		else {
			throw new NoSuchElementException();
		}
	}
	
	@RequestMapping(method=DELETE,value="/{id}")
	public void eliminarArbol(@PathVariable Long id){
		Optional<Arbol> arbol = servicio.getPorId(id);
		if(arbol.isPresent()){
			servicio.eliminar(arbol.get());
		}
		else {
			throw new NoSuchElementException();
		}
	}
}
