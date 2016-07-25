package ec.patrimonio.compartido.datos;
import java.util.Collection;
import javax.persistence.*;

@Entity
public class Familia {
      @Column
      private String descripcionFamilia;
      @Column
      private String nombreFamilia;
      @OneToMany(cascade=CascadeType.ALL,mappedBy="familia")
      private Collection<Arbol> arboles;

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(name = "id", nullable = false, updatable = false)
     private Long id;

     public Familia()
     {
     }

    public Long getId() {
        return id;
    }

    protected void setId(Long id){
        this.id = id;
    }

     public String getDescripcionFamilia() {
         return descripcionFamilia;
     }
     public void setDescripcionFamilia( String value ) {
         descripcionFamilia = value;
     }
     public String getNombreFamilia() {
         return nombreFamilia;
     }
     public void setNombreFamilia( String value ) {
         nombreFamilia = value;
     }
     public Collection<Arbol> getArboles() {
         return arboles;
     }
     public void setArboles( Collection<Arbol> value ) {
         arboles = value;
     }
}
