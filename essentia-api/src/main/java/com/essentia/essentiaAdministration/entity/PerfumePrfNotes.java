package com.essentia.essentiaAdministration.entity;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class PerfumePrfNotes {
    
	@ManyToOne
	@JoinColumn(name = "perfume")
    private Perfume perfume;
    @ManyToOne
	@JoinColumn(name = "note")
    private PerfumeNote note;
    private int type;
    public PerfumePrfNotes() {
    }

    public PerfumePrfNotes(Perfume perfume, PerfumeNote note, int type) {
        this.perfume = perfume;
        this.note = note;
        this.type = type;
    }
    public Perfume getPerfume() {
        return perfume;
    }

    public void setPerfume(Perfume perfume) {
        this.perfume = perfume;
    }

    public PerfumeNote getNote() {
        return note;
    }

    public void setNote(PerfumeNote note) {
        this.note = note;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
