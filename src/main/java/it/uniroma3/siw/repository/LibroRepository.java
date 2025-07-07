package it.uniroma3.siw.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import it.uniroma3.siw.model.Libro;

public interface LibroRepository extends CrudRepository<Libro, Long>{
	@Query("SELECT l FROM Libro l LEFT JOIN FETCH l.recensioni r LEFT JOIN FETCH r.utente WHERE l.id = :id")
    Optional<Libro> findByIdWithRecensioniAndUtenti(@Param("id") Long id);
	
	@Query("SELECT DISTINCT l FROM Libro l LEFT JOIN FETCH l.immagini")
    public List<Libro> findAllConImmaginiEAutori();
}
