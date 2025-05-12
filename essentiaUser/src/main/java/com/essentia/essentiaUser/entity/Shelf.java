package com.essentia.essentiauser.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "shelf")
public class Shelf {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String name;
	@ManyToOne
	@JoinColumn(name = "user")
	private User user;
	@ManyToMany
	@JoinTable(
			name = "perfume_shelf",
			joinColumns = @JoinColumn (name = "shelf"),
			inverseJoinColumns = @JoinColumn(name = "perfume")
			)	
	private List<Perfume> perfumes;

	public Shelf(){};

	public Shelf(String name, User user){
		this.name = name;
		this.user = user;
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
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<Perfume> getPerfumes() {
		return perfumes;
	}
	public void setPerfumes(List<Perfume> perfumes) {
		this.perfumes = perfumes;
	}
	public boolean addPerfume(Perfume perfume) {
		return this.perfumes.add(perfume);
	}
	public boolean removePerfume(Perfume perfume) {
		return this.perfumes.remove(perfume);
	}
	
}
