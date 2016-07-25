package ec.patrimonio.web.dto;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import com.google.common.base.Function;
import com.google.common.base.Optional;

import ec.patrimonio.compartido.datos.Arbol;
import ec.patrimonio.compartido.datos.Familia;
import ec.patrimonio.compartido.servicios.ServicioArbol;

public class FamiliaDTO {

	private Long id;
	private String descripcionFamilia;
	private String nombreFamilia;
	private Collection<Long> arboles;
	@Autowired
	private ServicioArbol servicio;
	private Function<Collection<Arbol>, Collection<Long>>
	arbol2Long = new Function<Collection<Arbol>, Collection<Long>>() {
		@Override
		public Collection<Long> apply(Collection<Arbol> arg0) {
		    Collection<Long> lista = new ArrayList<Long>();
		    for(Arbol a : arg0){
		    	lista.add(a.getId());
		    }
			return lista;
		}
	};
	
	private Function<Collection<Long>, Collection<Arbol>> 
	long2Arbol = new Function<Collection<Long>, Collection<Arbol>>() {
		
		@Override
		public Collection<Arbol> apply(Collection<Long> arg0) {
			Collection<Arbol> lista = new ArrayList<Arbol>();
			for(Long id: arg0){
				Optional<Arbol> a = servicio.getPorId(id);
				if(a.isPresent()){
					lista.add(a.get());
				}
				else {
					throw new DataIntegrityViolationException("La lista de arboles contiene uno que no existe");
				}
			}
			return lista;
		}
	};
	
	public FamiliaDTO(Long id, String descripcionFamilia, String nombreFamilia,
			Collection<Arbol> arboles) {
		super();
		this.id = id;
		this.descripcionFamilia = descripcionFamilia;
		this.nombreFamilia = nombreFamilia;
		this.arboles = arbol2Long.apply(arboles);
	}

	public FamiliaDTO(Familia familia){
		this.id = familia.getId();
		this.descripcionFamilia = familia.getDescripcionFamilia();
		this.nombreFamilia = familia.getNombreFamilia();
		this.arboles = arbol2Long.apply(familia.getArboles());
	}
	
	public Familia toFamilia(){
		Familia familia = new Familia();
		familia.setDescripcionFamilia(descripcionFamilia);
		familia.setNombreFamilia(nombreFamilia);
		familia.setArboles(long2Arbol.apply(arboles));
		return familia;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcionFamilia() {
		return descripcionFamilia;
	}

	public void setDescripcionFamilia(String descripcionFamilia) {
		this.descripcionFamilia = descripcionFamilia;
	}

	public String getNombreFamilia() {
		return nombreFamilia;
	}

	public void setNombreFamilia(String nombreFamilia) {
		this.nombreFamilia = nombreFamilia;
	}

}
