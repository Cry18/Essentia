package com.essentia.essentiaAdministration.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class PerfumeDto {

    @NotBlank(message = "il nome deve contenere almeno un carattere diverso da spazio")
    @Size(max = 30, message = "il nome del profumo può contenere al massimo 30 caratteri")  
    private String name;
    @NotBlank(message = "il brand deve contenere almeno un carattere diverso da spazio")
    @Size(max = 30, message = "il brand del profumo può contenere al massimo 30 caratteri")
    private String brand;
    @NotBlank(message = "la descrizione del profumo deve contenere almeno un carattere diverso da spazio")
    @Size(min = 10, message = "la descrizione deve contenere almeno 10 caratteri")
    private String description;

    @NotEmpty(message = "la lista delle note non può essere vuota")
    @Valid
    private List<PerfumePrfNotesDto> notes;
    
    @NotEmpty(message = "la lista dei profumieri non può essere vuota")
    //validazione del singolo profumiere
    private List<@NotBlank(message = "Il nome del profumiere non può essere vuoto") String> parfumers;

    public PerfumeDto() {
    }

    public PerfumeDto(String name, String brand, String description, List<PerfumePrfNotesDto> notes, List<String> parfumers) {
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.notes = notes;
        this.parfumers = parfumers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<PerfumePrfNotesDto> getNotes() {
        return notes;
    }

    public void setNotes(List<PerfumePrfNotesDto> notes) {
        this.notes = notes;
    }
    
    public List<String> getParfumers() {
        return parfumers;
    }

    public void setParfumers(List<String> parfumers) {
        this.parfumers = parfumers;
    }
}
