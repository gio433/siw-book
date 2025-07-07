package it.uniroma3.siw.model;

import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.time.LocalDate;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import java.util.Objects;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Autore {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    private String nome;
    @NotBlank
    private String cognome;
    @Past
    @NotNull
    private LocalDate dataNascita;
    @Past
    @NotNull
    private LocalDate dataMorte;
    @NotBlank
    private String nazionalita;
    @ManyToMany(mappedBy = "autori")
    private List<Libro> libri;
    @OneToMany(mappedBy = "autore", fetch = FetchType.EAGER)
    private List<Immagine> immagini = new ArrayList<>();
    
    //costruttore
    public Autore() {}

    // setter
    public void setId(Long id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setCognome(String cognome) { this.cognome = cognome; }
    public void setDataNascita(LocalDate dataNascita) { this.dataNascita = dataNascita; }
    public void setDataMorte(LocalDate dataMorte) { this.dataMorte = dataMorte; }
    public void setNazionalita(String nazionalita) { this.nazionalita = nazionalita; }
    public void setLibri(List<Libro> libri) { this.libri = libri; }
    public void setImmagine(List<Immagine> immagini) { this.immagini = immagini; }
    
    // getter
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getCognome() { return cognome; }
    public LocalDate getDataNascita() { return dataNascita; }
    public LocalDate getDataMorte() { return dataMorte; }
    public String getNazionalita() { return nazionalita; }
    public List<Libro> getLibri() { return this.libri; }
    public List<Immagine> getImmagini() { return this.immagini;}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Autore other = (Autore) obj;
		return Objects.equals(id, other.id);
	}
	
	@Override
	public String toString() {
	    return "Autore{" +
	           "id=" + id +
	           ", nome='" + nome + '\'' +
	           ", cognome='" + cognome + '\'' +
	           ", dataNascita=" + dataNascita +
	           '}';
	}
}
