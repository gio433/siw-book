package it.uniroma3.siw.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;

@Entity
public class Immagine {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeFile;
    private String tipoContenuto;
    @Lob
    private byte[] dati;
    @ManyToOne
    @JoinColumn(name = "autore_id")
    private Autore autore;
    @ManyToOne
    @JoinColumn(name = "libro_id")
    private Libro libro;
    
    //costruttore	
    public Immagine() {}
    
    public void setId(Long id) {
		this.id = id;
	}
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
	public void setTipoContenuto(String tipoContenuto) {
		this.tipoContenuto = tipoContenuto;
	}
    public void setDati(byte[] dati) {
		this.dati = dati;
	}
	public void setAutore(Autore autore) {
		this.autore = autore;
	}
	public void setLibro(Libro libro) {
		this.libro = libro;
	}
	
	// setter e getter
	public Long getId() {
		return id;
	}
	public String getNomeFile() {
		return nomeFile;
	}
	public String getTipoContenuto() {
		return tipoContenuto;
	}
	public byte[] getDati() {
		return dati;
	}
	public Autore getAutore() {
		return autore;
	}
	public Libro getLibro() {
		return libro;
	}
    
	@Override
	public String toString() {
	    return "Immagine{" +
	           "id=" + id +
	           ", nomeFile='" + nomeFile + '\'' +
	           ", tipoContenuto='" + tipoContenuto + '\'' +
	           '}';
	}
}
