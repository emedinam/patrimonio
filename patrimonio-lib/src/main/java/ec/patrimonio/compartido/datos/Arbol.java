package ec.patrimonio.compartido.datos;
import javax.persistence.*;

@Entity
public class Arbol {
      @Column
      private String nombreComun;
      @Column
      private String nombreCientifico;
      @ManyToOne
      @JoinColumn
      private Familia familia;
      @Column
      private String contenidoInformativo;
      @OneToOne(mappedBy="arbol")
      private ArbolRegistrado registro;

     public ArbolRegistrado getRegistro() {
		return registro;
	}

	public void setRegistro(ArbolRegistrado registro) {
		this.registro = registro;
	}
	@Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(name = "id", nullable = false, updatable = false)
     private Long id;

     public Arbol()
     {
     }

    public Long getId() {
        return id;
    }

    protected void setId(Long id){
        this.id = id;
    }

     public String getNombreComun() {
         return nombreComun;
     }
     public void setNombreComun( String value ) {
         nombreComun = value;
     }
     public String getNombreCientifico() {
         return nombreCientifico;
     }
     public void setNombreCientifico( String value ) {
         nombreCientifico = value;
     }
     public Familia getFamilia() {
         return familia;
     }
     public void setFamilia( Familia value ) {
         familia = value;
     }
     public String getContenidoInformativo() {
         return contenidoInformativo;
     }
     public void setContenidoInformativo( String value ) {
         contenidoInformativo = value;
     }
}
