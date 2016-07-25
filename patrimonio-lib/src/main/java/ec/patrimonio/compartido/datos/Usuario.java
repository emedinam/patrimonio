package ec.patrimonio.compartido.datos;


import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Table(name="usuarios")
@javax.persistence.Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name ="email", nullable=false, unique=true)
    private String email;

    @Column(name = "password_cifrado", nullable = false)
    private String passwordCifrado;

    @Column(name = "rol", nullable = false)
	@Enumerated(EnumType.STRING)    
    private Rol rol;
    
    /**
	 * Crea un nuevo objeto de este tipo
	 * @param email
	 * @param passwordHash
	 * @param rol
	 */
    public Usuario(String email, String passwordHash, Rol rol) {
        this.email = email;
        this.passwordCifrado = passwordHash;
        this.rol = rol;
    }

    /**
     *  Requerido por JPA
     */
    public Usuario() {
        super();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordCifrado;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordCifrado = passwordHash;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Long getId() {
        return id;
    }

}
