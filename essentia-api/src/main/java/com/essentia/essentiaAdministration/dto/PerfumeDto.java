package com.essentia.essentiaAdministration.dto;

import java.util.List;

public class PerfumeDto {

    private String name;
    private String brand;
    private String description;
    private List<PerfumePrfNotesDto> notes;
    private List<String> parfumers;

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
