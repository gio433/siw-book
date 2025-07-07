package it.uniroma3.siw.model;

import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Credentials {
	
	public static final String USER_ROLE = "USER";
    public static final String ADMIN_ROLE = "ADMIN";
    
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotBlank
	@Column(nullable= false, unique=true)
	private String username;
	@NotBlank
	@Column(nullable= false)
	private String password;
	@NotBlank
	@Column(nullable= false)
	private String role;
	@OneToOne
	@JoinColumn(name = "amministratore_id")
	private Amministratore amministratore;
	@OneToOne
	@JoinColumn(name = "utente_id")
	private Utente utente;
	
	public Credentials() {
		
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Amministratore getAmministratore() {
		return this.amministratore;
	}

	public void setAmministratore(Amministratore amministratore) {
		this.amministratore = amministratore;
	}

	public Utente getUtente() {
		return this.utente;
	}
	
	public void setUtente(Utente utente) {
		this.utente = utente;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(password, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Credentials other = (Credentials) obj;
		return Objects.equals(password, other.password) && Objects.equals(username, other.username);
	}
}
