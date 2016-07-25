package ec.patrimonio.compartido.datos;

import java.util.Collection;
import javax.persistence.*;


@Entity
public class ArbolRegistrado {
	@Column
	private String cedula;
	@Column
	private String tipo;
	@Column
	private String observaciones;
	@Lob
	private Byte[] foto;
	@OneToMany(mappedBy="arbolRegistrado",cascade=CascadeType.ALL)
	private Collection<SeguimientoArbol> seguimientos;
	@OneToOne
	@JoinColumn
	private Arbol arbol;

	public Arbol getArbol() {
		return arbol;
	}

	public void setArbol(Arbol arbol) {
		this.arbol = arbol;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;

	public ArbolRegistrado() {
	}

	public Long getId() {
		return id;
	}

	protected void setId(Long id) {
		this.id = id;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String value) {
		cedula = value;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String value) {
		tipo = value;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String value) {
		observaciones = value;
	}

	public Byte[] getFoto() {
		return foto;
	}

	public void setFoto(Byte[] value) {
		foto = value;
	}

	public Collection<SeguimientoArbol> getSeguimientos() {
		return seguimientos;
	}

	public void setSeguimientos(Collection<SeguimientoArbol> value) {
		seguimientos = value;
	}
}
