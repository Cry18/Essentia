package com.essentia.common.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class BrandDto {

    private int id;

    @NotBlank(message = "il nome deve contenere almeno un carattere diverso da spazio")
    @Size(max = 30, message = "il nome del brand può contenere al massimo 30 caratteri")
    private String name;

    @NotBlank(message = "la descrizione del brand deve contenere almeno un carattere diverso da spazio")
    @Size(min = 10, message = "la descrizione deve contenere almeno 10 caratteri")
    private String description;

    @NotBlank(message = "la nazionalità del brand deve contenere almeno un carattere diverso da spazio")
    @Size(max = 30, message = "la nazionalità del brand può contenere al massimo 30 caratteri")
    private String nationality;

    private String imageUrl;

    public BrandDto() {}

    public BrandDto(String name, String description, String nationality) {
        this.name = name;
        this.description = description;
        this.nationality = nationality;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getNationality() { return nationality; }
    public void setNationality(String nationality) { this.nationality = nationality; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
