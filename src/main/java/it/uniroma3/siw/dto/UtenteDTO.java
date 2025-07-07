package it.uniroma3.siw.dto;

public class UtenteDTO { //classe di appoggio per la vista
	
	private Long id;
	private String name;
	private String surname;
	
	public UtenteDTO(Long id, String name, String surname) {
		this(name,surname);
		this.id = id;
	}
	
	public UtenteDTO(String name, String surname) {
		this.name = name;
		this.surname = surname;
	}
	
	public UtenteDTO() {
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
	
}
