package com.essentia.essentiauser.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ReviewDto {
    
    private int id;
    @Size(max = 20, message = "il titolo della recensione può contenere al massimo 20 caratteri")
    private String title;
    @Size(max = 150, message = "la descrizione della recensione può contenere al massimo 150 caratteri")
	private String description;
    @NotNull(message = "Inserire il voto, compreso tra 1 e 5")
    @Min(value = 1, message = "Il voto deve essere compreso tra 1 e 5")	
    @Max(value = 5, message = "Il voto deve essere compreso tra 1 e 5")
	private Integer vote;
    @NotNull(message = "Inserire la stagionalità, compresa tra 1 e 4")
    @Min(value = 1, message = "Il punteggio deve essere compreso tra 1 e 4")	
    @Max(value = 4, message = "Il punteggio deve essere compreso tra 1 e 4")
	private Integer seasonality;
    @NotNull(message = "Inserire il genere, 0 per uomo e 1 per donna")
	private boolean gender;
    @NotNull(message = "Inserire il punteggio di scia, compreso tra 1 e 5")
    @Min(value = 1, message = "Il punteggio deve essere compreso tra 1 e 5")	
    @Max(value = 5, message = "Il punteggio deve essere compreso tra 1 e 5")
	private Integer sillage;
    @NotNull(message = "Inserire il punteggio di persistenza, compreso tra 1 e 5")
    @Min(value = 1, message = "Il punteggio deve essere compreso tra 1 e 5")	
    @Max(value = 5, message = "Il punteggio deve essere compreso tra 1 e 5")
	private Integer longevity;

    public ReviewDto() {
    }

    public ReviewDto(String title, String description, int vote, int seasonality, boolean gender, int sillage, int longevity) {
        this.title = title;
        this.description = description;
        this.vote = vote;
        this.seasonality = seasonality;
        this.gender = gender;
        this.sillage = sillage;
        this.longevity = longevity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    public int getSeasonality() {
        return seasonality;
    }

    public void setSeasonality(int seasonality) {
        this.seasonality = seasonality;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public int getSillage() {
        return sillage;
    }

    public void setSillage(int sillage) {
        this.sillage = sillage;
    }

    public int getLongevity() {
        return longevity;
    }

    public void setLongevity(int longevity) {
        this.longevity = longevity;
    }
    
}
