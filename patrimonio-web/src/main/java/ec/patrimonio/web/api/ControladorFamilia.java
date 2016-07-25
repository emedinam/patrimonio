package ec.patrimonio.web.api;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

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

import ec.patrimonio.compartido.datos.Familia;
import ec.patrimonio.compartido.servicios.ServicioFamilia;
import ec.patrimonio.web.dto.FamiliaDTO;

@RestController
@RequestMapping("/api/family")
public class ControladorFamilia {
	
	private ServicioFamilia servicio;
	private Function<Collection<Familia>,Collection<FamiliaDTO>> fam2dto;
	@Autowired
	public ControladorFamilia(ServicioFamilia servicio) {
		super();
		this.servicio = servicio;
		fam2dto = new Function<Collection<Familia>, Collection<FamiliaDTO>>() {
			
			@Override
			public Collection<FamiliaDTO> apply(Collection<Familia> arg0) {
				ArrayList<FamiliaDTO> lista = new ArrayList<FamiliaDTO>();
				for(Familia fam: arg0){
					lista.add(new FamiliaDTO(fam));
				}
				return lista;
			}
		};
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
	Collection<FamiliaDTO> todasLasFamilias(){
		return fam2dto.apply(servicio.getTodos());
	}
	
	@RequestMapping(value="/{id}",method=GET)
	public FamiliaDTO getFamilia(@PathVariable Long id){
		Optional<Familia> familia = servicio.getPorId(id);
		if(familia.isPresent()){
			return new FamiliaDTO(familia.get());
		}
		else{
			throw new NoSuchElementException();
		}
	}
	
	@RequestMapping(method=POST)
	public FamiliaDTO crearFamilia(@RequestBody FamiliaDTO dto){
		Familia fam = dto.toFamilia();
		servicio.crear(fam);
		return new FamiliaDTO(fam);
	}
	
	@RequestMapping(method=DELETE,value="/{id}")
	public void eliminarFamilia(@PathVariable Long id){
		Optional<Familia> familia = servicio.getPorId(id);
		if(familia.isPresent()){
			servicio.eliminar(familia.get());
		}
		else {
			throw new NoSuchElementException();
		}
	}
}
