package ec.patrimonio.compartido.datos;
import javax.persistence.*;

@Entity
public class FotoSeguimiento {
      @Column
      private String descripcion;
    @Lob
      private Byte[] imagen;

     @ManyToOne
     @JoinColumn
	private SeguimientoArbol seguimiento;
     
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(name = "id", nullable = false, updatable = false)
     private Long id;

     public FotoSeguimiento()
     {
     }

    public Long getId() {
        return id;
    }

    protected void setId(Long id){
        this.id = id;
    }

     public String getDescripcion() {
         return descripcion;
     }
     public void setDescripcion( String value ) {
         descripcion = value;
     }
     public Byte[] getImagen() {
         return imagen;
     }
     public void setImagen( Byte[] value ) {
         imagen = value;
     }

	public SeguimientoArbol getSeguimiento() {
		return seguimiento;
	}

	public void setSeguimiento(SeguimientoArbol seguimiento) {
		this.seguimiento = seguimiento;
	}
}
