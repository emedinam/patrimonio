package ec.patrimonio.web.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import com.google.common.base.Optional;

import ec.patrimonio.compartido.datos.Arbol;
import ec.patrimonio.compartido.datos.ArbolRegistrado;
import ec.patrimonio.compartido.datos.Familia;
import ec.patrimonio.compartido.servicios.ServicioArbolRegistrado;
import ec.patrimonio.compartido.servicios.ServicioFamilia;

/**
 *  DTO para evitar serializar grandes cantidades de datos.
 *  Familia posee un @OneToMany con Arbol asi que devolver
 *  una Familia implica devolver todos los arboles en ella.
 * @author jadex
 *
 */
public class ArbolDTO {
	
	private Long id;
    private String nombreComun;
    private String nombreCientifico;
    private Long familia;
    private String contenidoInformativo;
    @Autowired
    private ServicioFamilia servFamilia;
    private Long registro;
    @Autowired
	private ServicioArbolRegistrado servRegistro;
    
    public ArbolDTO(Arbol arbol){
    this.setId(arbol.getId());
    this.nombreComun = arbol.getNombreComun();
    this.nombreCientifico = arbol.getNombreCientifico();
    this.familia = arbol.getFamilia().getId();
    this.contenidoInformativo = this.getContenidoInformativo();
    this.registro = arbol.getRegistro().getId();
    }
    
	public ArbolDTO(Long id,String nombreComun, String nombreCientifico, Long familia, String contenidoInformativo, Long registro) {
		super();
		this.id = id;
		this.nombreComun = nombreComun;
		this.nombreCientifico = nombreCientifico;
		this.familia = familia;
		this.contenidoInformativo = contenidoInformativo;
		this.registro = registro;
	}

	public Arbol toArbol(){
		Arbol arbol = new Arbol();
		arbol.setContenidoInformativo(contenidoInformativo);
		arbol.setNombreCientifico(nombreCientifico);
		arbol.setNombreComun(nombreComun);
		Optional<Familia> fam = servFamilia.getPorId(familia);
		if(fam.isPresent()){
			arbol.setFamilia(fam.get());
		}
		Optional<ArbolRegistrado> reg = servRegistro.getPorId(registro);
		if(reg.isPresent()){
			arbol.setRegistro(reg.get());
		}
		return arbol;
	}
	public String getNombreComun() {
		return nombreComun;
	}

	public void setNombreComun(String nombreComun) {
		this.nombreComun = nombreComun;
	}

	public String getNombreCientifico() {
		return nombreCientifico;
	}

	public void setNombreCientifico(String nombreCientifico) {
		this.nombreCientifico = nombreCientifico;
	}

	public Long getFamilia() {
		return familia;
	}

	public void setFamilia(Long familia) {
		this.familia = familia;
	}

	public String getContenidoInformativo() {
		return contenidoInformativo;
	}

	public void setContenidoInformativo(String contenidoInformativo) {
		this.contenidoInformativo = contenidoInformativo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRegistro() {
		return registro;
	}

	public void setRegistro(Long registrado) {
		this.registro = registrado;
	}
    
}
