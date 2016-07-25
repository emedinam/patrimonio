package ec.patrimonio.web.api;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Optional;

import ec.patrimonio.compartido.datos.SeguimientoArbol;
import ec.patrimonio.compartido.servicios.ServicioArbol;
import ec.patrimonio.compartido.servicios.ServicioSeguimientoArbol;
import ec.patrimonio.web.dto.SeguimientoArbolDTO;

@RestController
@RequestMapping("/api/tree/{id}/seguimiento")
public class ControladorSeguimiento {

	private ServicioArbol servicio;
	private ServicioSeguimientoArbol servSeg;
	
	@Autowired
	public ControladorSeguimiento(ServicioArbol servicio, ServicioSeguimientoArbol servSeg) {
		super();
		this.servicio = servicio;
		this.servSeg = servSeg;
	}
	
	@RequestMapping(method=GET, value="/{idSeg}")
	   public SeguimientoArbolDTO getSeg(@PathVariable Long id, @PathVariable Long idSeg) {
		   if(servicio.getPorId(id).isPresent() && servSeg.getPorId(id).get().getArbolRegistrado() != null){
			   Optional<SeguimientoArbol> seg = servSeg.getPorId(idSeg);
			   if (seg.isPresent()){
				   return new SeguimientoArbolDTO(seg.get());
			   }
			   else {
				   throw new NoSuchElementException("El seguimiento no existe");
			   }
		   }
		   else {
			   throw new NoSuchElementException("No existe el arbol o no tiene seguimientos");
		   }
	   }
	
	   @RequestMapping(method=POST)
	   public SeguimientoArbolDTO setSeg(@PathVariable Long id,SeguimientoArbolDTO dto) {
		   if(servicio.getPorId(id).isPresent() && servSeg.getPorId(id).get().getArbolRegistrado() != null){
			   SeguimientoArbol seg = dto.toSeguimientoArbol();
			   servSeg.crear(seg);
			   return new SeguimientoArbolDTO(seg);
		   }
		   else {
			   throw new NoSuchElementException("No existe el arbol o no tiene seguimientos");
		   }
	   }
	
	   @RequestMapping(method=DELETE,value="/{idSeg}")
	   public void eliminarSeg(@PathVariable Long id, @PathVariable Long idSeg) {
		   if(servicio.getPorId(id).isPresent() && servSeg.getPorId(id).get().getArbolRegistrado() != null){
			   Optional<SeguimientoArbol> seg = servSeg.getPorId(idSeg);
			   if(seg.isPresent()){
				   servSeg.eliminar(seg.get());
			   }
			   else {
				   throw new NoSuchElementException("El seguimiento no existe");
			   }
		   }
		   else {
			   throw new NoSuchElementException("No existe el arbol o no tiene seguimientos");
		   }
	   }
}
