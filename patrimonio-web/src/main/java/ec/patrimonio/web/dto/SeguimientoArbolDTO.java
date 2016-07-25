package ec.patrimonio.web.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import com.google.common.base.Function;
import com.google.common.base.Optional;

import ec.patrimonio.compartido.datos.ArbolRegistrado;
import ec.patrimonio.compartido.datos.FotoSeguimiento;
import ec.patrimonio.compartido.datos.SeguimientoArbol;
import ec.patrimonio.compartido.servicios.ServicioArbolRegistrado;
import ec.patrimonio.compartido.servicios.ServicioFotoSeguimiento;

public class SeguimientoArbolDTO {
	private Long id;
    private Date fecha;
    private String ejecutor;
    private String acciones;
    private Long arbolRegistrado;
    private Collection<Long> fotos;
    @Autowired
    private ServicioArbolRegistrado servicioRegistro;
    @Autowired
    private ServicioFotoSeguimiento servFotos;
    private Function <Collection<Long>, Collection<FotoSeguimiento>>
    long2Fotos = new Function<Collection<Long>, Collection<FotoSeguimiento>>() {

		@Override
		public Collection<FotoSeguimiento> apply(Collection<Long> arg0) {
			ArrayList<FotoSeguimiento> lista = new ArrayList<FotoSeguimiento>();
			for (Long fotoId : arg0) {
				Optional<FotoSeguimiento> seg = servFotos.getPorId(fotoId);
				if(seg.isPresent()){
					lista.add(seg.get());
				}
				else {
					throw new DataIntegrityViolationException("Error en la lista de fotos");
				}
			}
			return lista;
		}
	};
	private Function <Collection<FotoSeguimiento>, Collection<Long>>
	fotos2Long = new Function<Collection<FotoSeguimiento>, Collection<Long>>() {
		
		@Override
		public Collection<Long> apply(Collection<FotoSeguimiento> arg0) {
			ArrayList<Long> lista = new ArrayList<Long>();
			for (FotoSeguimiento foto : arg0) {
				lista.add(foto.getId());
			}
			return lista;
		}
	};
	public SeguimientoArbolDTO(Long id,Date fecha, String ejecutor, String acciones, Long arbolRegistrado,
			Collection<Long> fotos) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.ejecutor = ejecutor;
		this.acciones = acciones;
		this.arbolRegistrado = arbolRegistrado;
		this.fotos = fotos;
	}
	
	public SeguimientoArbolDTO(SeguimientoArbol seg){
		this.acciones = seg.getAcciones();
		this.ejecutor = seg.getEjecutor();
		this.fecha = seg.getFecha();
		this.arbolRegistrado = seg.getArbolRegistrado().getId();
		this.fotos = fotos2Long.apply(seg.getFotos());
		this.id = seg.getId();
	}
	
	public SeguimientoArbol toSeguimientoArbol(){
		SeguimientoArbol nuevo = new SeguimientoArbol();
		nuevo.setAcciones(acciones);
		nuevo.setEjecutor(ejecutor);
		nuevo.setFecha(fecha);
		nuevo.setFotos(long2Fotos.apply(fotos));
		Optional<ArbolRegistrado> ar = servicioRegistro.getPorId(arbolRegistrado);
		if(ar.isPresent()){
			nuevo.setArbolRegistrado(ar.get());
		}
		return nuevo;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getEjecutor() {
		return ejecutor;
	}
	public void setEjecutor(String ejecutor) {
		this.ejecutor = ejecutor;
	}
	public String getAcciones() {
		return acciones;
	}
	public void setAcciones(String acciones) {
		this.acciones = acciones;
	}
	public Long getArbolRegistrado() {
		return arbolRegistrado;
	}
	public void setArbolRegistrado(Long arbolRegistrado) {
		this.arbolRegistrado = arbolRegistrado;
	}
	public Collection<Long> getFotos() {
		return fotos;
	}
	public void setFotos(Collection<Long> fotos) {
		this.fotos = fotos;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
    
}
