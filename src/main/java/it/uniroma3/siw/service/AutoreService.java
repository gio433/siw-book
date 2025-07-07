package it.uniroma3.siw.service;

import org.springframework.stereotype.Service;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import it.uniroma3.siw.model.Autore;
import it.uniroma3.siw.repository.AutoreRepository;
import jakarta.transaction.Transactional;

@Service
public class AutoreService {
	@Autowired
    private AutoreRepository autoreRepository;

    public List<Autore> findAll() {
        return (List<Autore>) autoreRepository.findAll();
    }
    
    @Transactional
    public Autore findById(Long id) {
        Autore autore = this.autoreRepository.findById(id).orElse(null);
        if(autore != null) {
        	Hibernate.initialize(autore.getLibri());
        	Hibernate.initialize(autore.getImmagini());
        }
        return autore;
    }

    public Autore save(Autore autore) {
        return autoreRepository.save(autore);
    }

    public void deleteById(Long id) {
        autoreRepository.deleteById(id);
    }
    
}
