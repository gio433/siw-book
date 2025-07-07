package it.uniroma3.siw.security;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import it.uniroma3.siw.model.Credentials;

@Configuration
@EnableWebSecurity
public class AuthConfiguration {
	
    @Autowired
	private DataSource dataSource;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
			.dataSource(dataSource)
			.usersByUsernameQuery("SELECT username, password, 1 as enabled FROM credentials WHERE username=?")
			.authoritiesByUsernameQuery("SELECT username, role FROM credentials WHERE username=?");
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); //di default la strength è 10
	}
	
	@Bean
	protected SecurityFilterChain configure(final HttpSecurity httpSecurity) throws Exception {
		
		httpSecurity
		.csrf(csrf -> csrf.disable())
		.authorizeHttpRequests(authorize -> authorize
				//chiunque accede a questi parametri
				.requestMatchers(HttpMethod.GET,"/","/index","/register","/login","/css/**","/images/**","/favicon.ico","/viewAutore/**","/libri/**",
						"/recensioni/**","/styleAutori.css","/styleIndex.css","/styleLogin.css","/styleModificaLibro.css",
						"/styleNewAutore.css","/styleNewRecensione.css","/styleRecensioni.css","/styleRegister.css","/styleViewAutore.css",
						"styleViewImmagine.css","/immagini/**","/viewImmagine/**").permitAll()
				//chiunque può mandare richieste POST al punto di accesso per login e register
				.requestMatchers(HttpMethod.POST,"/login","/register").permitAll()
				.requestMatchers(HttpMethod.GET, "/utente/newRecensione").hasAnyAuthority(Credentials.USER_ROLE)
	            .requestMatchers(HttpMethod.POST, "/utente/newRecensione").hasAnyAuthority(Credentials.USER_ROLE)
				.requestMatchers(HttpMethod.GET,"/utente/**").hasAnyAuthority(Credentials.USER_ROLE)
				.requestMatchers(HttpMethod.POST,"/utente/**").hasAnyAuthority(Credentials.USER_ROLE)
				.requestMatchers(HttpMethod.GET,"/amministratore/**").hasAnyAuthority(Credentials.ADMIN_ROLE)
				.requestMatchers(HttpMethod.POST,"/amministratore/**").hasAnyAuthority(Credentials.ADMIN_ROLE)
				//tutti gli utenti autenticati possono accedere alle pagine rimanenti
				.anyRequest().authenticated()
		)
		// LOGIN: qui definiamo il login
		.formLogin(formLogin -> formLogin
				.loginPage("/login")
				.permitAll()
				.successHandler(new AuthHandler()) //pagina di reindirizzamento se il login ha successo
				.failureUrl("/login?error=true")
		)
		// LOGOUT: qui definiamo il logout
		.logout(logout -> logout
				.logoutUrl("/logout") 
				.logoutSuccessUrl("/index")
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.clearAuthentication(true).permitAll()
		);

		return httpSecurity.build();
		
		}
	
}
