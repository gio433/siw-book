package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import it.uniroma3.siw.model.Libro;
import it.uniroma3.siw.service.LibroService;

@Controller
public class LibroController {
	@Autowired
	private LibroService libroService;
	
	@GetMapping("/viewImmagine/{id}")
    public String viewImmagine(@PathVariable("id") Long id, Model model) {
    	Libro libro = libroService.findById(id);
    	if(libro != null) {
    		model.addAttribute("libro", libro);
    	} else {
    		 model.addAttribute("messaggioErroreLibro", "Il libro con ID " + id + " non è stato trovato.");
    	}
    	return "viewImmagine";
    }
}
