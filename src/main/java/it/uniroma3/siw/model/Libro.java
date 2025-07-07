package it.uniroma3.siw.model;

import jakarta.persistence.Id;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    private String titolo;
    private int anno;
    @ManyToMany
    private List<Autore> autori;
    @OneToMany(mappedBy = "libro")
    private List<Recensione> recensioni;
    @OneToMany(mappedBy = "libro", 
            fetch = FetchType.EAGER, 
            cascade = CascadeType.REMOVE,
            orphanRemoval = true)
    private List<Immagine> immagini = new ArrayList<>();
    
    
    //costruttore
    public Libro() {}

    // setter
    public void setId(Long id) { this.id = id; }
    public void setTitolo(String titolo) { this.titolo = titolo; }
    public void setAnno(int anno) { this.anno = anno; }
    public void setAutori(List<Autore> autori) { this.autori = autori; }
    public void setRecensioni(List<Recensione> recensioni) { this.recensioni = recensioni; }
    public void setImmagini(List<Immagine> immagini) { this.immagini = immagini; }

    // getter
    public Long getId() { return id; }
    public String getTitolo() { return titolo; }
    public int getAnno() { return anno; }
    public List<Autore> getAutori() { return this.autori; }
    public List<Recensione> getRecensioni() { return recensioni; }
    public List<Immagine> getImmagini() { return this.immagini;}
    
    @Override
    public String toString() {
        return "Libro{" +
               "id=" + id +
               ", titolo='" + titolo + '\'' +
               ", anno=" + anno +
               '}';
    }
}
