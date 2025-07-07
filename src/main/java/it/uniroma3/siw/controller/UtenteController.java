package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import it.uniroma3.siw.dto.UtenteDTO;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Libro;
import it.uniroma3.siw.model.Recensione;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.LibroService;
import it.uniroma3.siw.service.RecensioneService;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/utente")
public class UtenteController {

    @Autowired
    private RecensioneService recensioneService;
    @Autowired
    private CredentialsService credentialsService;
    @Autowired
    private LibroService libroService;
    
    private Utente utenteCorrente;
	
	/* ritorna il riferimento al paziente attualmente loggato */
	private Utente getUtenteCorrente() {
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
        return (credentials != null) ? credentials.getUtente() : null;
	}
	
	
	@GetMapping
    public String homeUtente(Model model) {
		this.utenteCorrente = getUtenteCorrente(); //mantengo il riferimento dopo l'accesso
		UtenteDTO utenteDTO = new UtenteDTO(utenteCorrente.getNome(), utenteCorrente.getCognome());
		List<Libro> libri = (List<Libro>) libroService.findAll();
        model.addAttribute("libri", libri);
        model.addAttribute("utente", utenteDTO);
        return "/index";
    }
	
    @GetMapping("/recensioni/{id}")
    public String visualizzaRecensioni(@Valid @PathVariable("id") Long id, BindingResult bindingResult, Model model) {
        List<Recensione> recensioni = recensioneService.findAll();
        model.addAttribute("recensioni", recensioni);
        return "recensioni" + "/" + id;
    }
	
    @GetMapping("/newrecensione")
    public String recensioneForm(Model model) {
        List<Libro> libri = libroService.findAll();
        if(libri != null) {
        	model.addAttribute("tuttiILibri", libri);
        }
        else {
        	model.addAttribute("Messaggio libri mancanti", "Non ci sono libri nel nostro catalogo.");
        }
    	model.addAttribute("recensione", new Recensione());
        return "newRecensione";
    }

	@PostMapping("/newrecensione")
	public String inserisciRecensione(@Valid @ModelAttribute("recensione") Recensione recensione, BindingResult bindingResult,
		    						@RequestParam("libroId") Long libroId, Model model, RedirectAttributes redirectAttributes) {
		
		Libro libroSelezionato = libroService.findById(libroId);
		if (bindingResult.hasErrors()) {
            model.addAttribute("tuttiILibri", (List<Libro>) libroService.findAll());
            return "newRecensione";
        }
		if (utenteCorrente != null && libroSelezionato != null) {
	        if (recensioneService.esisteRecensionePerUtenteELibro(utenteCorrente, libroSelezionato)) {
	            bindingResult.rejectValue("libro", "recensione.duplicata", "Hai già inviato una recensione per questo libro.");
	        }
		}
		// Controlla TUTTI gli errori accumulati in bindingResult
	    if (bindingResult.hasErrors()) {
	        System.out.println("DEBUG: BindingResult ha errori. Ritorno al form newRecensione.");
	        bindingResult.getAllErrors().forEach(error -> System.out.println("Error: " + error.getCode() + " - " + error.getDefaultMessage()));
	        try {
	            model.addAttribute("tuttiILibri", libroService.findAll());
	        } catch (Exception e) {
	            model.addAttribute("tuttiILibri", new ArrayList<Libro>());
	        }
	        return "newRecensione"; // Torna al form per mostrare gli errori
	    }
		recensione.setLibro(libroSelezionato);
		recensione.setUtente(utenteCorrente);
		recensioneService.save(recensione);
		redirectAttributes.addFlashAttribute("inserisciRecensioneSuccesso", "Recensione inserita con successo");
		return "redirect:/index";   
	}
}
