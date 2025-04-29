package com.essentia.essentia_api.entity;

import java.util.List;


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
		
		@ManyToMany
		@JoinTable(
				name = "perfume_prfnotes",
				joinColumns = @JoinColumn (name = "perfume"),
				inverseJoinColumns = @JoinColumn(name = "note")
				)
		private List<Perfume_note> perfumeNotes;
		
		@OneToMany(mappedBy = "perfume")
		private List<Review> reviews;
		
		public Brand getBrand() {
			return brand;
		}
		public void setBrand(Brand brand) {
			this.brand = brand;
		}
		public List<Parfumer> getNasi() {
			return parfumers;
		}
		public void setNasi(List<Parfumer> nasi) {
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

}
