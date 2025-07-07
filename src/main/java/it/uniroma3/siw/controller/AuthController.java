package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.security.AuthHandler;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.UtenteService;
import jakarta.validation.Valid;

@Controller
public class AuthController {
	
	@Autowired
	private CredentialsService credentialsService;
	
	@Autowired
	private UtenteService utenteService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
    private AuthHandler authHandler;
	
	@GetMapping("/register") 
	public String newRegister(Model model){
		model.addAttribute("utente", new Utente());
		model.addAttribute("credentials", new Credentials());
		return "register";
	}
	
	@PostMapping("/register")
	public String completeRegister(@Valid @ModelAttribute("utente") Utente utente,
								   BindingResult utenteBindingResult,
								   @Valid @ModelAttribute("credentials") Credentials credentials,
								   BindingResult credentialsBindingResult,
								   Model model) {
			
		Utente savedUtente = this.utenteService.save(utente);
		credentials.setUtente(savedUtente);
		credentials.setRole(Credentials.USER_ROLE);
		credentials.setPassword(this.passwordEncoder.encode(credentials.getPassword()));
		this.credentialsService.save(credentials);
		
		return "redirect:/login?registerSuccess";
	}
	
	@GetMapping("/login")
	public String login(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
        	String url = authHandler.determinaUrl(authentication);
            return "redirect:" + url;
        }
   
        return "login.html";
    }
	
	
}
