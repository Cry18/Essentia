package com.essentia.essentiaadministration.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Column;
import jakarta.persistence.Table;

@Entity
@Table(name = "brand")
public class Brand {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String name;
	@Column(columnDefinition = "TEXT")
	private String description;
	private String nationality;
	private String imageUrl;

	@OneToMany(mappedBy = "brand")
	private List<Perfume> perfumes = new ArrayList<>();
	public int getId() {
		return id;
	}

	public Brand() {
	}

	public Brand(String name, String description, String nationality) {
		this.name = name;
		this.description = description;
		this.nationality = nationality;
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

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public List<Perfume> getPerfumes() {
		return perfumes;
	}

	public void setPerfumes(List<Perfume> perfumes) {
		this.perfumes = perfumes;
	}


}