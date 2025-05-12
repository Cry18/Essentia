package com.essentia.essentiauser.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ShelfDto {
    
    private int id;
    @NotBlank(message = "il nome deve contenere almeno un carattere diverso da spazio")
    @Size(max = 30, message = "il nome dello scaffale può contenere al massimo 30 caratteri")
    private String name;

    List<String> perfumes;

    public ShelfDto(){};

    public ShelfDto(String name){
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPerfumes() {
        return perfumes;
    }

    public void setPerfumes(List<String> perfumes) {
        this.perfumes = perfumes;
    }

    

}
