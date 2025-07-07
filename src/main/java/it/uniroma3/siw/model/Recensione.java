package it.uniroma3.siw.model;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"libro_id", "utente_id"}))
public class Recensione {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    private String titolo;
    @NotNull
    @Min(1)
    @Max(5)
    private int voto;
    @Column(length = 2000)
    @NotBlank
    private String testo;
    @ManyToOne
    @JoinColumn(name = "libro_id")
    private Libro libro;
    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;
    
    //costruttore
    public Recensione() {}

    // setter
    public void setId(Long id) { this.id = id; }
    public void setTitolo(String titolo) { this.titolo = titolo; }
    public void setVoto(int voto) { this.voto = voto; }
    public void setTesto(String testo) { this.testo = testo; }
    public void setLibro(Libro libro) { this.libro = libro; }
    public void setUtente(Utente utente) { this.utente = utente; }

    // getter
    public Long getId() { return id; }
    public String getTitolo() { return titolo; }
    public int getVoto() { return voto; }
    public String getTesto() { return testo; }
    public Libro getLibro() { return this.libro; }
    public Utente getUtente() { return this.utente; }
}
