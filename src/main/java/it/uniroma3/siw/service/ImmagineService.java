package it.uniroma3.siw.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // <-- IMPORT NECESSARIO
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Autore;
import it.uniroma3.siw.model.Immagine;
import it.uniroma3.siw.model.Libro;
import it.uniroma3.siw.repository.AutoreRepository;
import it.uniroma3.siw.repository.ImmagineRepository;
import it.uniroma3.siw.repository.LibroRepository;

@Service
public class ImmagineService {

    private final ImmagineRepository immagineRepository;
    private final AutoreRepository autoreRepository;
    private final LibroRepository libroRepository;

    public ImmagineService(ImmagineRepository immagineRepository,
                           AutoreRepository autoreRepository,
                           LibroRepository libroRepository) {
        this.immagineRepository = immagineRepository;
        this.autoreRepository = autoreRepository;
        this.libroRepository = libroRepository;
    }

    @Transactional
    public Immagine salvaImmaginePerAutore(Long autoreId, MultipartFile file) throws IOException {
        Autore autore = autoreRepository.findById(autoreId)
                .orElseThrow(() -> new IllegalArgumentException("Autore non trovato con ID: " + autoreId));

        Immagine img = new Immagine();
        img.setNomeFile(file.getOriginalFilename());
        img.setTipoContenuto(file.getContentType());
        img.setDati(file.getBytes());
        img.setAutore(autore);

        autore.getImmagini().add(img);

        return immagineRepository.save(img);
    }

    @Transactional
    public Immagine salvaImmaginePerLibro(Long libroId, MultipartFile file) throws IOException {
        Libro libro = libroRepository.findById(libroId)
                .orElseThrow(() -> new IllegalArgumentException("Libro non trovato con ID: " + libroId));

        Immagine img = new Immagine();
        img.setNomeFile(file.getOriginalFilename());
        img.setTipoContenuto(file.getContentType());
        img.setDati(file.getBytes());
        img.setLibro(libro);

        libro.getImmagini().add(img);

        return immagineRepository.save(img);
    }

    public Optional<Immagine> trovaPerId(Long id) {
        return immagineRepository.findById(id);
    }

}