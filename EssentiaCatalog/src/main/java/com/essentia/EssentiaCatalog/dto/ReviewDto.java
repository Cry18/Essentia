package com.essentia.essentiacatalog.dto;

public class ReviewDto {
    String user;
    String title;
    String description;
    int vote;
    int seasonality;
    boolean gender;
    int sillage;
    int longevity;

    public ReviewDto() {
    }

    public ReviewDto(String user, String title, String description, int vote, int seasonality, boolean gender, int sillage, int longevity) {
        this.user = user;
        this.title = title;
        this.description = description;
        this.vote = vote;
        this.seasonality = seasonality;
        this.gender = gender;
        this.sillage = sillage;
        this.longevity = longevity;
    }
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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
