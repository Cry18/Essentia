package com.essentia.essentiauser.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
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
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(unique = true, nullable = false)
	private String username;

	@Column(nullable = false)
	private String password;

	private String name;
	private String surname;
	private String imageUrl;

	/** true = ROLE_ADMIN, false = ROLE_USER */
	private boolean role;
	
	@OneToOne
	@JoinColumn(name="signature",referencedColumnName = "id")
	private Perfume signature;
	@OneToMany(mappedBy = "user")
	private List<Shelf> shelfs;
	@ManyToMany
	@JoinTable(
			name = "favorites",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "perfume")
			)
	private List<Perfume> favorites = new ArrayList<>();
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public boolean isRole() {
		return role;
	}
	public void setRole(boolean role) {
		this.role = role;
	}
	public Perfume getSignature() {
		return signature;
	}
	public void setSignature(Perfume signature) {
		this.signature = signature;
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

	public void addFavorite(Perfume perfume) {
    if (!favorites.contains(perfume)) {
        favorites.add(perfume);
    }
}

public boolean removeFavorite(Perfume perfume) {
    return favorites.remove(perfume);
}

}
