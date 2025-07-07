package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import it.uniroma3.siw.dto.AmministratoreDTO;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Amministratore;
import it.uniroma3.siw.model.Autore;
import it.uniroma3.siw.model.Libro;
import it.uniroma3.siw.model.Recensione;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.ImmagineService;
import it.uniroma3.siw.service.LibroService;
import it.uniroma3.siw.service.RecensioneService;
import jakarta.validation.Valid;
import it.uniroma3.siw.service.AutoreService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AmministratoreController {

    @Autowired
    private LibroService libroService;
    @Autowired
    private CredentialsService credentialsService;
    @Autowired
    private AutoreService autoreService;
    @Autowired
    private RecensioneService recensioneService;
    @Autowired
    private ImmagineService immagineService;
    
    /* ritorna il riferimento all'amministratore attualmente loggato */
	private Amministratore getAmministratoreCorrente() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal(); //ci fornisce i dati di autenticazione
        String username;
        
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername(); 
        }
        else { 
        	//se non è un oggetto UserDetails usiamo una stringa corrispondente.
            username = principal.toString();
        }
        //recuperiamo l'oggetto credentials tramite valore username
        Credentials credentials = this.credentialsService.findByUsername(username);
        return (credentials != null) ? credentials.getAmministratore() : null;
	}
	
	@GetMapping("/amministratore")
    public String homeAmministratore(Model model) {
		Amministratore amministratoreCorrente = getAmministratoreCorrente();
		AmministratoreDTO amministratoreDTO = new AmministratoreDTO(amministratoreCorrente.getNome(), amministratoreCorrente.getCognome());
		List<Libro> libri = (List<Libro>) libroService.findAll();;
        model.addAttribute("libri", libri);
        model.addAttribute("amministratore", amministratoreDTO);
        return "/index";
    }
	
    @GetMapping("/amministratore/newLibro")
    public String showLibroForm(Model model) {
        model.addAttribute("libro", new Libro());
        List<Autore> autori = (List<Autore>) autoreService.findAll();
        model.addAttribute("tuttiGliAutori", autori);
        return "newLibro";
    }
    
    @PostMapping("/amministratore/newLibro")
    public String aggiungiLibro(@Valid @ModelAttribute("libro") Libro libro,
                                BindingResult bindingResult,
                                @RequestParam(name = "autoriSelezionatiIds", required = false) List<Long> autoriSelezionatiIds,
                                @RequestParam("files") List<MultipartFile> files, // <-- 5. RICEVI IL FILE
                                RedirectAttributes redirectAttributes, // <-- 6. PER I MESSAGGI
                                Model model) {

        if (autoriSelezionatiIds == null || autoriSelezionatiIds.isEmpty()) {
            bindingResult.rejectValue("autori", "NotEmpty.libro.autori", "È necessario selezionare almeno un autore.");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("tuttiGliAutori", autoreService.findAll());
            return "newLibro";
        }

        // Associa autori
        List<Autore> autoriDelLibro = new ArrayList<>();
        for (Long autoreId : autoriSelezionatiIds) {
            autoriDelLibro.add(autoreService.findById(autoreId));
        }
        libro.setAutori(autoriDelLibro);

        // 7. PRIMA salva il libro per ottenere un ID
        libroService.save(libro);

        // 8. POI, se il file esiste, salvalo e associalo al libro
        if (files != null && !files.isEmpty()) {
            int fileSalvati = 0;
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    try {
                        immagineService.salvaImmaginePerLibro(libro.getId(), file);
                        fileSalvati++;
                    } catch (IOException e) {
                        // Gestisce l'errore per un singolo file
                        System.err.println("Errore durante il salvataggio del file: " + file.getOriginalFilename());
                    }
                }
            }
            if (fileSalvati > 0) {
                redirectAttributes.addFlashAttribute("aggiungiLibroSuccesso", "Libro e immagini salvati con successo!");
            } else {
                redirectAttributes.addFlashAttribute("aggiungiLibroSuccesso", "Libro salvato (nessuna immagine caricata).");
            }
        } else {
            redirectAttributes.addFlashAttribute("aggiungiLibroSuccesso", "Libro salvato con successo (senza immagini).");
        }
        
        return "redirect:/index";
    }
    
    @GetMapping("/amministratore/newAutore")
    public String showAutoreForm(Model model) {
    	model.addAttribute("autore", new Autore());
    	return "newAutore";
    }
    
    @PostMapping("/amministratore/newAutore")
    public String aggiungiAutore(@Valid @ModelAttribute("autore") Autore autore,
                                 BindingResult bindingResult,
                                 @RequestParam("file") MultipartFile file, // <-- RICEVI IL FILE
                                 RedirectAttributes redirectAttributes) {

    	if (bindingResult.hasErrors()) {
            return "newAutore";
        }

        // 7. PRIMA salva l'autore per ottenere un ID
        autoreService.save(autore);

        // 8. POI, se il file esiste, salvalo e associalo all'autore
        if (file != null && !file.isEmpty()) {
            try {
                immagineService.salvaImmaginePerAutore(autore.getId(), file);
                redirectAttributes.addFlashAttribute("aggiungiAutoreSuccesso", "Autore e foto salvati con successo!");
            } catch (IOException e) {
                 redirectAttributes.addFlashAttribute("messaggioErrore", "Autore salvato, si è verificato un errore durante il caricamento della foto.");
                 return "redirect:/viewAutore/" + autore.getId();
            }
        } else {
            redirectAttributes.addFlashAttribute("aggiungiAutoreSuccesso", "Autore salvato con successo (senza foto).");
        }

        return "redirect:/index";
    }
    
    @GetMapping("/viewAutore/{id}")
    public String getDettaglioAutore(@PathVariable("id") Long id, Model model) {
    	Autore autore = autoreService.findById(id);
    	if(autore != null) {
    		model.addAttribute("autore", autore);
    	} else {
    		 model.addAttribute("messaggioErroreAutore", "L'autore con ID " + id + " non è stato trovato.");
    	}
    	return "viewAutore";
    }
    
    @GetMapping("/amministratore/cancellalibro/{libroId}")
    public String cancellaLibro(@PathVariable("libroId") Long id, RedirectAttributes redirectAttributes) {
    	libroService.deleteById(id);
    	redirectAttributes.addFlashAttribute("cancellaLibroSuccesso", "Libro cancellato correttamente");
    	return "redirect:/index";
    }
    
    @PostMapping("/amministratore/cancellaautore/{autoreId}")
    public String cancellaAutore(@PathVariable("autoreId") Long id, RedirectAttributes redirectAttributes) {
    	Autore autore = autoreService.findById(id);
    	if(!autore.getLibri().isEmpty()) {
    		redirectAttributes.addFlashAttribute("cancellaAutoreErrore", "L'autore non può essere cancellato perché sono presenti dei libri che ha scritto");
    		return "redirect:/amministratore/autori";
    	} else {
    		autoreService.deleteById(id);
    	}
    	redirectAttributes.addFlashAttribute("cancellaAutoreSuccesso", "Autore cancellato correttamente");
    	return "redirect:/amministratore/autori";
    }
    
    @GetMapping("/amministratore/autori")
    public String vediAutori(Model model) {
    	List<Autore> autori = autoreService.findAll();
    	model.addAttribute("autori", autori);
    	return "autori";
    }
    
    @GetMapping("/recensioni/libro/{id}")
    public String mostraRecensioni(@PathVariable("id") Long libroId, Model model) {
    	Libro libro = libroService.findByIdConRecensioni(libroId);
    	List<Recensione> recensioniDelLibro = libro.getRecensioni();
    	model.addAttribute("libro", libro);
    	model.addAttribute("recensioni", recensioniDelLibro);
    	return "recensioni";
    }
    
    @PostMapping("/amministratore/cancellarecensione/{idRecensione}")
    public String processCancellaRecensione(@PathVariable("idRecensione") Long id, RedirectAttributes redirectAttributes) {
    	recensioneService.deleteById(id);
    	redirectAttributes.addFlashAttribute("cancellaRecensioneSuccesso", "Recensione cancellata correttamente");
    	return "redirect:/index";
    }
    
    @GetMapping("/amministratore/modificalibro/{id}")
    public String modificaLibro(@PathVariable("id") Long id, Model model) {
        Libro libro = libroService.findById(id);
        model.addAttribute("libro", libro);
        model.addAttribute("tuttiGliAutori", autoreService.findAll());
        return "modificaLibro";
    }

    @PostMapping("/amministratore/modificalibro/{id}")
    public String postModificaLibro(@Valid @ModelAttribute("libro") Libro libro,
    								BindingResult bindingResult,
    								@RequestParam(name = "autori", required = false) List<Long> autori,
    								@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        
    	if (bindingResult.hasErrors()) {
            libro.setId(id); 
            model.addAttribute("libro", libro);
            model.addAttribute("tuttiGliAutori", autoreService.findAll());
            return "modificaLibro";
        }
    	List<Autore> autoriLibro = new ArrayList<>();
    	if (autori != null) {
    	    for (Long autoreId : autori) {
    	        Autore autore = autoreService.findById(autoreId);
    	        if (autore != null) {
    	            autoriLibro.add(autore);
    	        }
    	    }
    	}
    	Libro libroModificato = libroService.findById(id);
    	libroModificato.setTitolo(libro.getTitolo());
        libroModificato.setAnno(libro.getAnno());
        libroModificato.setAutori(autoriLibro);
    	libroService.save(libroModificato);
    	redirectAttributes.addFlashAttribute("modificaLibroSuccesso", "Libro modificato con successo");
        return "redirect:/index";
    }
    
}
