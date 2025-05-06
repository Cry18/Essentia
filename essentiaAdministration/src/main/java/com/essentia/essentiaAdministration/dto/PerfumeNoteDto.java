package com.essentia.essentiaAdministration.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PerfumeNoteDto {

	@NotBlank(message = "il nome deve contenere almeno un carattere diverso da spazio")
	@Size(max = 30, message = "il nome della nota può contenere al massimo 30 caratteri")
	private String name;
	@NotBlank(message = "la descrizione della nota deve contenere almeno un carattere diverso da spazio")
	@Size(min = 10, message = "la descrizione deve contenere almeno 10 caratteri")
	private String description;
	
	public PerfumeNoteDto() {}
	
	public PerfumeNoteDto (String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	
	
}
