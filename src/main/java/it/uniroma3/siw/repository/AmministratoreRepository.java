package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;
import it.uniroma3.siw.model.Amministratore;

public interface AmministratoreRepository extends CrudRepository<Amministratore, Long> {
	
}
