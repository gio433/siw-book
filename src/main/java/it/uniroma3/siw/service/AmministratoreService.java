package it.uniroma3.siw.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import it.uniroma3.siw.model.Amministratore;
import it.uniroma3.siw.repository.AmministratoreRepository;

@Service
public class AmministratoreService {
	@Autowired
    private AmministratoreRepository amministratoreRepository;

    public List<Amministratore> findAll() {
        return (List<Amministratore>) amministratoreRepository.findAll();
    }

    public Amministratore findById(Long id) {
        return amministratoreRepository.findById(id).get();
    }

    public Amministratore save(Amministratore amministratore) {
        return amministratoreRepository.save(amministratore);
    }

    public void deleteById(Long id) {
        amministratoreRepository.deleteById(id);
    }
}
