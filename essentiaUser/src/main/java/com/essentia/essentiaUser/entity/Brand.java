package com.essentia.essentiauser.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "brand")
public class Brand {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String name;
	private String description;
	private String nazionality;
	
	@OneToMany(mappedBy = "brand",cascade = CascadeType.ALL)
	private List<Perfume> perfumes = new ArrayList<>();
	public int getId() {
		return id;
	}

	public Brand() {
	}

	public Brand(String name, String description, String nazionality) {
		this.name = name;
		this.description = description;
		this.nazionality = nazionality;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNazionality() {
		return nazionality;
	}

	public void setNazionality(String nazionality) {
		this.nazionality = nazionality;
	}

	public List<Perfume> getPerfumes() {
		return perfumes;
	}

	public void setPerfumes(List<Perfume> perfumes) {
		this.perfumes = perfumes;
	}


}
