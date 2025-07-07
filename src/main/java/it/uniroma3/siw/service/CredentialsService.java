package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.repository.CredentialsRepository;


@Service
public class CredentialsService {
	
	@Autowired
	private CredentialsRepository credentialsRepository;
	
	public Credentials getCredentialsById(Long id) {
		return credentialsRepository.findById(id).get();
	}
	
	public Credentials findByUsername(String username){
		return credentialsRepository.findByUsername(username);
	}
	
    public Credentials save(Credentials credentials) {
        return credentialsRepository.save(credentials);
    }
	
}