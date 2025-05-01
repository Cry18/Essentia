package com.essentia.essentiaAdministration.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PerfumePrfNotesDto {
    @NotBlank(message = "il nome deve contenere almeno un carattere diverso da spazio")
    @Size(max = 30, message = "il nome della nota può contenere al massimo 30 caratteri")
    private String name;
    @NotBlank(message = "il tipo deve contenere almeno un carattere diverso da spazio")
    @Size(min = 1, message = "il tipo della nota deve essere compreso tra 1 e 3")
    @Size(max = 3, message = "il tipo della nota deve essere compreso tra 1 e 3")	
    private int type;

    public PerfumePrfNotesDto() {
    }

    public PerfumePrfNotesDto(String name, int type) {
        this.name = name;
        this.type = type;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
