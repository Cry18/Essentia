package com.essentia.essentiauser.dto;

import java.util.List;

public class UserProfileDto {

    private String username;
    private String name;
    private String surname;
    private String imageUrl;

    /** Profumo firma — null se non impostato */
    private PerfumeSummaryDto signature;

    /** Lista profumi preferiti */
    private List<PerfumeSummaryDto> favorites;

    /** Lista scaffali con i profumi contenuti */
    private List<ShelfDto> shelves;

    public UserProfileDto() {}

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public PerfumeSummaryDto getSignature() { return signature; }
    public void setSignature(PerfumeSummaryDto signature) { this.signature = signature; }
    public List<PerfumeSummaryDto> getFavorites() { return favorites; }
    public void setFavorites(List<PerfumeSummaryDto> favorites) { this.favorites = favorites; }
    public List<ShelfDto> getShelves() { return shelves; }
    public void setShelves(List<ShelfDto> shelves) { this.shelves = shelves; }
}
