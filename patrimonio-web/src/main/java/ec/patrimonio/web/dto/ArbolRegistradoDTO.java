package ec.patrimonio.web.dto;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import com.google.common.base.Function;
import com.google.common.base.Optional;

import ec.patrimonio.compartido.datos.Arbol;
import ec.patrimonio.compartido.datos.ArbolRegistrado;
import ec.patrimonio.compartido.datos.SeguimientoArbol;
import ec.patrimonio.compartido.servicios.ServicioArbol;
import ec.patrimonio.compartido.servicios.ServicioSeguimientoArbol;

public class ArbolRegistradoDTO {
	private String cedula;
	private String tipo;
	private String observaciones;
	private Byte[] foto;
	private Collection<Long> seguimientos;
	private Long id;
	private Long arbol;
	
	@Autowired
	private ServicioSeguimientoArbol servicioSeguimiento;
	@Autowired
	private ServicioArbol servicioArbol;
	private Function<Collection<Long>,Collection<SeguimientoArbol>>
	long2Seguimiento = new Function<Collection<Long>, Collection<SeguimientoArbol>>() {
		
		@Override
		public Collection<SeguimientoArbol> apply(Collection<Long> arg0) {
			ArrayList<SeguimientoArbol> lista = new ArrayList<SeguimientoArbol>();
			for (Long id : arg0) {
				Optional<SeguimientoArbol> seg = servicioSeguimiento.getPorId(id);
				if(seg.isPresent()){
					lista.add(seg.get());
				}
				else {
					throw new DataIntegrityViolationException("La lista de seguimientos no es valida");
				}
			}
			return lista;
		}
	};
	private Function<Collection<SeguimientoArbol>,Collection<Long>>
	seguimiento2Long = new Function<Collection<SeguimientoArbol>, Collection<Long>>() {
		
		@Override
		public Collection<Long> apply(Collection<SeguimientoArbol> arg0) {
			ArrayList<Long> lista = new ArrayList<Long>();
			for (SeguimientoArbol seguimientoArbol : arg0) {
				lista.add(seguimientoArbol.getId());
			}
			return lista;
		}
	};
	
	
	public ArbolRegistradoDTO(Long id,String cedula, String tipo, String observaciones, Byte[] foto,
			Collection<Long> seguimientos, Long arbol) {
		super();
		this.id = id;
		this.cedula = cedula;
		this.tipo = tipo;
		this.observaciones = observaciones;
		this.foto = foto;
		this.seguimientos = seguimientos;
		this.arbol = arbol;
	}
	public ArbolRegistradoDTO(ArbolRegistrado aRegistrado){
		this.id = aRegistrado.getId();
		this.tipo = aRegistrado.getTipo();
		this.cedula = aRegistrado.getCedula();
		this.foto = aRegistrado.getFoto();
		this.observaciones = aRegistrado.getObservaciones();
		this.seguimientos = seguimiento2Long.apply(aRegistrado.getSeguimientos());
		this.arbol = aRegistrado.getArbol().getId();
	}
	public ArbolRegistrado toArbolRegistrado(){
		ArbolRegistrado nuevo = new ArbolRegistrado();
		nuevo.setCedula(cedula);
		nuevo.setFoto(foto);
		nuevo.setObservaciones(observaciones);
		nuevo.setTipo(tipo);
		nuevo.setSeguimientos(long2Seguimiento.apply(seguimientos));
		Optional<Arbol> ar = servicioArbol.getPorId(arbol);
		if(ar.isPresent()){
			nuevo.setArbol(ar.get());
		}
		return nuevo;
	}
	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public Byte[] getFoto() {
		return foto;
	}

	public void setFoto(Byte[] foto) {
		this.foto = foto;
	}

	public Collection<Long> getSeguimientos() {
		return seguimientos;
	}

	public void setSeguimientos(Collection<Long> seguimientos) {
		this.seguimientos = seguimientos;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getArbol() {
		return arbol;
	}
	public void setArbol(Long arbol) {
		this.arbol = arbol;
	}
	
}
