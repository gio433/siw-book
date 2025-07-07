package it.uniroma3.siw.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.UtenteRepository;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    public List<Utente> findAll() {
        return (List<Utente>) utenteRepository.findAll();
    }

    public Utente findById(Long id) {
        return utenteRepository.findById(id).get();
    }

    public Utente save(Utente utente) {
        return utenteRepository.save(utente);
    }

    public void deleteById(Long id) {
        utenteRepository.deleteById(id);
    }

}
