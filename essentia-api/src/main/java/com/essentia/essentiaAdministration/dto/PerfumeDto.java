package com.essentia.essentiaAdministration.dto;

public class PerfumeDto {

    private String name;
    private String brand;
    private String description;
    private String notes; // JSON string of note IDs

    public PerfumeDto() {
    }

    public PerfumeDto(int id, String name, String brand, String description, String imageUrl, String notes) {
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.notes = notes;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
