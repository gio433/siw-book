package it.uniroma3.siw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import it.uniroma3.siw.model.Libro;
import it.uniroma3.siw.service.LibroService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class MainController {

	@Autowired
	private LibroService libroService;
	
	@GetMapping({"/", "/index"})
	public String index(Model model) {
		// dati necessari per la pagina index.
        List<Libro> libri = (List<Libro>) libroService.findAll();
        model.addAttribute("libri", libri);
        return "index";
    }
}