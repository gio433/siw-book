package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;
import it.uniroma3.siw.model.Libro;
import it.uniroma3.siw.model.Recensione;
import it.uniroma3.siw.model.Utente;

public interface RecensioneRepository extends CrudRepository<Recensione, Long>{
	boolean existsByUtenteAndLibro(Utente utente, Libro libro);
}
