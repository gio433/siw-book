package it.uniroma3.siw.service;

import org.springframework.stereotype.Service;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;
import it.uniroma3.siw.model.Libro;
import it.uniroma3.siw.repository.LibroRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;
    
    @Transactional
    public List<Libro> findAll() {
        List<Libro> libri = (List<Libro>) libroRepository.findAll();
        for (Libro libro : libri) {
            Hibernate.initialize(libro.getAutori());
            Hibernate.initialize(libro.getImmagini());
        }
        return libri;
    }

    @Transactional
    public Libro findById(Long id) {
    	Libro libro = libroRepository.findById(id).orElse(null);
        if (libro != null) {
            Hibernate.initialize(libro.getImmagini());
        }
        return libro;
    }
    
    @Transactional
    public Libro save(Libro libro) {
        return libroRepository.save(libro);
    }

    public void deleteById(Long id) {
        libroRepository.deleteById(id);
    }
    
    @Transactional(readOnly = true)
    public Libro findByIdConRecensioni(Long id) {
        Optional<Libro> result = libroRepository.findByIdWithRecensioniAndUtenti(id);
        return result.orElse(null);
    }
    
    @Transactional
    public List<Libro> findAllConImmaginiEAutori() {
        return this.libroRepository.findAllConImmaginiEAutori();
    }
}