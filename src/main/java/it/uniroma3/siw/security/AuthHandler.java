package it.uniroma3.siw.security;

import java.io.IOException;
import java.util.Collection;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.security.web.DefaultRedirectStrategy;
import it.uniroma3.siw.model.Credentials;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthHandler implements AuthenticationSuccessHandler {
	
	private DefaultRedirectStrategy redirect = new DefaultRedirectStrategy();
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
										Authentication authentication) throws IOException, ServletException {
		
		String url = determinaUrl(authentication);
		redirect.sendRedirect(request,response,url);
	}
	
	public String determinaUrl(Authentication authentication) {
		//estraggo le authority dall'autenticazione effettuata
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		
		for(GrantedAuthority a : authorities) {
			String name = a.getAuthority();
			if(name.equals(Credentials.ADMIN_ROLE))
				return "/amministratore";
			else if (name.equals(Credentials.USER_ROLE))
				return "/utente";
		}
		return "/index";
	}
}
