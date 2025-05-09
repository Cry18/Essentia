package com.essentia.essentiaadministration.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PerfumeDto {

    private int id;
    @NotBlank(message = "il nome deve contenere almeno un carattere diverso da spazio")
    @Size(max = 30, message = "il nome del profumo può contenere al massimo 30 caratteri")  
    private String name;
    @NotNull(message = "il brand non può essere null")
    private int brand;
    @NotBlank(message = "la descrizione del profumo deve contenere almeno un carattere diverso da spazio")
    @Size(min = 10, message = "la descrizione deve contenere almeno 10 caratteri")
    private String description;

    @NotEmpty(message = "la lista delle note non può essere vuota")
    @Valid
    private List<PerfumePrfNotesDto> notes;
    
    @NotEmpty(message = "la lista dei profumieri non può essere vuota")
    //validazione del singolo profumiere
    private List<@NotNull(message = "Il profumiere non può essere null") Integer> parfumers;

    public PerfumeDto() {
    }

    public PerfumeDto(String name, int brand, String description, List<PerfumePrfNotesDto> notes, List<Integer> parfumers) {
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.notes = notes;
        this.parfumers = parfumers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBrand() {
        return brand;
    }

    public void setBrand(int brand) {
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
    
    public List<Integer> getParfumers() {
        return parfumers;
    }

    public void setParfumers(List<Integer> parfumers) {
        this.parfumers = parfumers;
    }
}
