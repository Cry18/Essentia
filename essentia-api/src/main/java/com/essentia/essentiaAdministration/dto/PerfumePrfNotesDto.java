package com.essentia.essentiaAdministration.dto;

public class PerfumePrfNotesDto {
    private String name;
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
