package com.essentia.essentiaadministration.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class PerfumePrfNotesDto {

    @NotNull(message = "La nota non può essere null")
    private int noteId;
    @NotNull(message = "Inserire il tipo della nota, compreso tra 1 e 3")
    @Min(value = 1, message = "Il tipo deve essere compreso tra 1 e 3")	
    @Max(value = 3, message = "Il tipo deve essere compreso tra 1 e 3")
    private Integer type;

    public PerfumePrfNotesDto() {
    }

    public PerfumePrfNotesDto(int noteId, int type) {
        this.noteId = noteId;
        this.type = type;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
