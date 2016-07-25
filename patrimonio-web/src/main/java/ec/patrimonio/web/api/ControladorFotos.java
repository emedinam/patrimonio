package ec.patrimonio.web.api;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

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

import com.google.common.base.Optional;

import ec.patrimonio.compartido.datos.Arbol;
import ec.patrimonio.compartido.datos.FotoSeguimiento;
import ec.patrimonio.compartido.datos.SeguimientoArbol;
import ec.patrimonio.compartido.servicios.ServicioArbol;
import ec.patrimonio.compartido.servicios.ServicioFotoSeguimiento;
import ec.patrimonio.compartido.servicios.ServicioSeguimientoArbol;
import ec.patrimonio.web.dto.FotoDTO;

@RestController
@RequestMapping("/api/tree/{id}/seguimiento/{idSeg}/fotos")
public class ControladorFotos {

	private ServicioArbol servicio;
	private ServicioSeguimientoArbol servSeg;
	private ServicioFotoSeguimiento servFotos;
	
	@Autowired
	public ControladorFotos(ServicioArbol servicio, ServicioFotoSeguimiento servFotos,
			ServicioSeguimientoArbol servSeg) {
		super();
		this.servicio = servicio;
		this.servFotos = servFotos;
		this.servSeg = servSeg;
	}

	@ExceptionHandler(NoSuchElementException.class)
    ResponseEntity<String> noSeEncuentra(NoSuchElementException ex){
        return new ResponseEntity<String>(ex.getLocalizedMessage(), HttpStatus.NOT_FOUND);
    }
	
	@ExceptionHandler(DataIntegrityViolationException.class)
    ResponseEntity<String> errorEnDatos(DataIntegrityViolationException ex){
        return new ResponseEntity<String>(ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
    }
	
   @RequestMapping(method=GET, value="/{idFoto}")
   public FotoDTO getFoto(@PathVariable Long id, @PathVariable Long idSeg, 
		   @PathVariable Long idFoto){
	   if(servicio.getPorId(id).isPresent() && servSeg.getPorId(idSeg).isPresent()){
		   Optional<FotoSeguimiento> foto = servFotos.getPorId(idFoto);
		   if (foto.isPresent()){
			   return new FotoDTO(foto.get());
		   }
		   else {
			   throw new NoSuchElementException("No existe la foto");
		   }
	   }
	   else {
		   throw new NoSuchElementException("No existe el arbol o no tiene seguimientos");
	   }
   }
   
   @RequestMapping(method=POST)
   public FotoDTO setFoto(@PathVariable Long id, @PathVariable Long idSeg,
		   @RequestBody FotoDTO dto){
	   if(servicio.getPorId(id).isPresent() && servSeg.getPorId(idSeg).isPresent()){
		   FotoSeguimiento foto = dto.toFotoSeguimiento();
		   SeguimientoArbol seg = servSeg.getPorId(idSeg).get();
		   foto.setSeguimiento(seg);
		   servFotos.crear(foto);
		   return new FotoDTO(foto);
	   }
	   else {
		   throw new NoSuchElementException("No existe el arbol o no tiene seguimientos");
	   }
   }
   
   @RequestMapping(method=PUT, value="/{idFoto}")
   public void actualizarFoto(@PathVariable Long id, @PathVariable Long idSeg, 
		   @PathVariable Long idFoto,
		   @RequestBody FotoDTO foto){
	   if(servicio.getPorId(id).isPresent() && servSeg.getPorId(idSeg).isPresent()){
		   Optional<FotoSeguimiento> actual = servFotos.getPorId(idFoto);
		   if (actual.isPresent()){
			   FotoSeguimiento actualizar = actual.get();
			   FotoSeguimiento nuevo = foto.toFotoSeguimiento();
			   actualizar.setDescripcion(nuevo.getDescripcion());
			   actualizar.setImagen(nuevo.getImagen());
			   actualizar.setSeguimiento(nuevo.getSeguimiento());
			   servFotos.actualizar(actualizar);
		   }
		   else {
			   throw new NoSuchElementException("No existe la foto");
		   }
	   }
	   else {
		   throw new NoSuchElementException("No existe el arbol o no tiene seguimientos");
	   }
   }
   
   @RequestMapping(method=DELETE, value="/{idFoto}")
   public void eliminarFoto(@PathVariable Long id, @PathVariable Long idSeg, 
		   @PathVariable Long idFoto){
	   if(servicio.getPorId(id).isPresent() && servSeg.getPorId(idSeg).isPresent()){
		   Optional<FotoSeguimiento> foto = servFotos.getPorId(idFoto);
		   if (foto.isPresent()){
			   servFotos.eliminar(foto.get());
		   }
		   else {
			   throw new NoSuchElementException("No existe la foto");
		   }
	   }
	   else {
		   throw new NoSuchElementException("No existe el arbol o no tiene seguimientos");
	   }
   }
}
