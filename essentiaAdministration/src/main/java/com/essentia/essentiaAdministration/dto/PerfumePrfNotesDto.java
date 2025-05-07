package com.essentia.essentiaAdministration.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PerfumePrfNotesDto {

    @NotBlank(message = "il nome deve contenere almeno un carattere diverso da spazio")
    @Size(max = 30, message = "il nome della nota può contenere al massimo 30 caratteri")
    private String name;
    @NotNull(message = "Inserire il tipo della nota, compreso tra 1 e 3")
    @Min(value = 1, message = "Il tipo deve essere compreso tra 1 e 3")	
    @Max(value = 3, message = "Il tipo deve essere compreso tra 1 e 3")
    private Integer type;

    public PerfumePrfNotesDto() {
    }

    public PerfumePrfNotesDto(String name, int type) {
        this.name = name;
        this.type = type;
    }

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
