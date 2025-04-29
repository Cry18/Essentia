package com.essentia.essentia_api.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String name;
	private String surname;
	private boolean role;
	
	@OneToOne
	@JoinColumn(name="signature",referencedColumnName = "id")
	private Perfume Signature;
	@OneToMany(mappedBy = "user")
	private List<Shelf> shelfs;
	@ManyToMany
	@JoinTable(
			name = "favorites",
			joinColumns = @JoinColumn (name = "perfume"),
			inverseJoinColumns = @JoinColumn(name = "user")
			)
	private List<Perfume> favorites;
	
	
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
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public boolean isRole() {
		return role;
	}
	public void setRole(boolean role) {
		this.role = role;
	}
	public Perfume getSignature() {
		return Signature;
	}
	public void setSignature(Perfume signature) {
		Signature = signature;
	}
	public List<Shelf> getShelfs() {
		return shelfs;
	}
	public void setShelfs(List<Shelf> shelfs) {
		this.shelfs = shelfs;
	}
	public List<Perfume> getFavorites() {
		return favorites;
	}
	public void setFavorites(List<Perfume> favorites) {
		this.favorites = favorites;
	}
}
