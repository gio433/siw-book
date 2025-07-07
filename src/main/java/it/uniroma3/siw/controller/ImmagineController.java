package it.uniroma3.siw.controller;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import it.uniroma3.siw.service.ImmagineService;

@Controller
@RequestMapping("/immagini")
public class ImmagineController {

    private ImmagineService immagineService;

    public ImmagineController(ImmagineService immagineService) {
        this.immagineService = immagineService;
    }

    // Upload per Autore
    @PostMapping("/upload/autore/{autoreId}")
    public String uploadAutore(@PathVariable Long autoreId,
                               @RequestParam("file") MultipartFile file,
                               RedirectAttributes redirectAttributes) {
        try {
            immagineService.salvaImmaginePerAutore(autoreId, file);
            //messaggio di successo
            redirectAttributes.addFlashAttribute("messaggio", "Immagine caricata con successo!");
        } catch (IOException | IllegalArgumentException e) {
           //messaggio di errore
            redirectAttributes.addFlashAttribute("errore", "Errore durante il caricamento: " + e.getMessage());
        }
        
        return "redirect:/autori/" + autoreId; 
    }

    // Upload per Libro
    @PostMapping("/upload/libro/{libroId}")
    public String uploadLibro(@PathVariable Long libroId,
                              @RequestParam("file") MultipartFile file,
                              RedirectAttributes redirectAttributes) {
        try {
            immagineService.salvaImmaginePerLibro(libroId, file);
            redirectAttributes.addFlashAttribute("messaggio", "Immagine caricata con successo!");
        } catch (IOException | IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errore", "Errore durante il caricamento: " + e.getMessage());
        }
        
        return "redirect:/libri/" + libroId;
    }

    // Visualizza immagine
    @GetMapping("/{id}")
    public ResponseEntity<byte[]> mostraImmagine(@PathVariable Long id) {
        return immagineService.trovaPerId(id)
            .map(img -> ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + img.getNomeFile() + "\"")
                .contentType(MediaType.parseMediaType(img.getTipoContenuto()))
                .body(img.getDati()))
            .orElse(ResponseEntity.notFound().build());
    }
}