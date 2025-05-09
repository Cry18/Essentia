package com.essentia.essentiacatalog.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "perfume")
public class Perfume {
	
		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		private int id;
		private String name;
		private String description;
		
		@ManyToOne
		@JoinColumn(name = "brand")
		private Brand brand;
		
		@ManyToMany
		@JoinTable(
				name = "perfume_parfumer",
				joinColumns = @JoinColumn (name = "perfume"),
				inverseJoinColumns = @JoinColumn(name = "parfumer")
				)
		private List<Parfumer> parfumers;

		@OneToMany(mappedBy = "perfume", cascade= CascadeType.ALL)
		private List<PerfumePrfNotes> PerfumePrfNotes = new ArrayList<>();

		
		@OneToMany(mappedBy = "perfume", cascade= CascadeType.ALL)
		private List<Review> reviews = new ArrayList<>();

	public Perfume() {
	}

	public Perfume(String name, String description, Brand brand, List<Parfumer> parfumers) {
		this.name = name;
		this.description = description;
		this.brand = brand;
		this.parfumers = parfumers;
	}

	public void addReview(Review review) {
		if (this.reviews != null) {
			this.reviews.add(review);
		}
	}

		public List<Review> getReviews() {
			return reviews;
		}

		public void setReviews(List<Review> reviews) {
			this.reviews = reviews;
		}
		
		public Brand getBrand() {
			return brand;
		}
		public void setBrand(Brand brand) {
			this.brand = brand;
		}
		public List<Parfumer> getParfumers() {
			return parfumers;
		}
		public void setParfumers(List<Parfumer> nasi) {
			this.parfumers = nasi;
		}
		public int getId() {
			return id;
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

		public List<PerfumePrfNotes> getPerfumePrfNotes() {
			return PerfumePrfNotes;
		}

		public void setPerfumePrfNotes(List<PerfumePrfNotes> PerfumePrfNotes) {
			this.PerfumePrfNotes = PerfumePrfNotes;
		}
}
