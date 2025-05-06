package com.essentia.EssentiaCatalog.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
@Entity
@Table(name = "perfume_prfnotes")
public class PerfumePrfNotes {
    
    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
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
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
