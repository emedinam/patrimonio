package ec.patrimonio.web.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import ec.patrimonio.compartido.datos.FotoSeguimiento;
import ec.patrimonio.compartido.servicios.ServicioSeguimientoArbol;

public class FotoDTO {
	
    private String descripcion;
    private Byte[] imagen;
    private Long seguimiento;
    private Long id;
    
    @Autowired
    private ServicioSeguimientoArbol servicio;
    
	public FotoDTO(String descripcion, Byte[] imagen, Long seguimiento, Long id) {
		super();
		this.descripcion = descripcion;
		this.imagen = imagen;
		this.seguimiento = seguimiento;
		this.id = id;
	}
	
	public FotoDTO(FotoSeguimiento foto){
		this.descripcion = foto.getDescripcion();
		this.imagen = foto.getImagen();
		this.id = foto.getId();
		this.seguimiento = foto.getSeguimiento().getId();
	}
	
	public FotoSeguimiento toFotoSeguimiento(){
		FotoSeguimiento foto = new FotoSeguimiento();
		foto.setDescripcion(descripcion);
		foto.setImagen(imagen);
		if(servicio.getPorId(seguimiento).isPresent()){
			foto.setSeguimiento(servicio.getPorId(seguimiento).get());
			return foto;
		}
		else {
			throw new DataIntegrityViolationException("La foto pertenece a un seguimiento inexistente");
		}
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Byte[] getImagen() {
		return imagen;
	}
	public void setImagen(Byte[] imagen) {
		this.imagen = imagen;
	}
	public Long getSeguimiento() {
		return seguimiento;
	}
	public void setSeguimiento(Long seguimiento) {
		this.seguimiento = seguimiento;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
    
    
}
