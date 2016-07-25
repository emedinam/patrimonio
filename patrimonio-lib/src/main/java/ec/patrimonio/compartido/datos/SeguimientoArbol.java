package ec.patrimonio.compartido.datos;
import java.util.Date;
import java.util.Collection;
import javax.persistence.*;

@Entity
public class SeguimientoArbol {
      @Column
      private Date fecha;
      @Column
      private String ejecutor;
      @Column
      private String acciones;
    @ManyToOne
    @JoinColumn
      private ArbolRegistrado arbolRegistrado;
    @OneToMany(mappedBy="seguimiento",cascade=CascadeType.ALL)
      private Collection<FotoSeguimiento> fotos;

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(name = "id", nullable = false, updatable = false)
     private Long id;

     public SeguimientoArbol()
     {
     }

    public Long getId() {
        return id;
    }

    protected void setId(Long id){
        this.id = id;
    }

     public Date getFecha() {
         return fecha;
     }
     public void setFecha( Date value ) {
         fecha = value;
     }
     public String getEjecutor() {
         return ejecutor;
     }
     public void setEjecutor( String value ) {
         ejecutor = value;
     }
     public String getAcciones() {
         return acciones;
     }
     public void setAcciones( String value ) {
         acciones = value;
     }
     public ArbolRegistrado getArbolRegistrado() {
         return arbolRegistrado;
     }
     public void setArbolRegistrado( ArbolRegistrado value ) {
         arbolRegistrado = value;
     }
     public Collection<FotoSeguimiento> getFotos() {
         return fotos;
     }
     public void setFotos( Collection<FotoSeguimiento> value ) {
         fotos = value;
     }
}
