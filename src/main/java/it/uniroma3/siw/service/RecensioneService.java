package it.uniroma3.siw.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

import it.uniroma3.siw.model.Libro;
import it.uniroma3.siw.model.Recensione;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.RecensioneRepository;
//import jakarta.transaction.Transactional;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RecensioneService {

    @Autowired
    private RecensioneRepository recensioneRepository;
    
    @Transactional(readOnly = true)
    public List<Recensione> findAll() {
        return (List<Recensione>) recensioneRepository.findAll();
    }

    public Recensione findById(Long id) {
        return recensioneRepository.findById(id).get();
    }
    
    @Transactional
    public Recensione save(Recensione recensione) {
        return recensioneRepository.save(recensione);
    }

    public void deleteById(Long id) {
        recensioneRepository.deleteById(id);
    }
    
    @Transactional(readOnly = true)
    public boolean esisteRecensionePerUtenteELibro(Utente utente, Libro libro) {
        return recensioneRepository.existsByUtenteAndLibro(utente, libro);
    }
}
