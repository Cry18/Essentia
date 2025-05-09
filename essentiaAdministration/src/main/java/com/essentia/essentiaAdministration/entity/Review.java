package com.essentia.essentiaadministration.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "reviews")
public class Review {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name = "perfume")
	private Perfume perfume;
	@ManyToOne
	@JoinColumn(name = "user")
	private User user;
	
	private String title;
	private String description;
	private int vote;
	private int seasonality;
	private boolean gender;
	private int sillage;
	private int longevity;
	
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
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getVote() {
		return vote;
	}
	public void setVote(int vote) {
		this.vote = vote;
	}
	public int getSeasonality() {
		return seasonality;
	}
	public void setSeasonality(int seasonality) {
		this.seasonality = seasonality;
	}
	public boolean isGender() {
		return gender;
	}
	public void setGender(boolean gender) {
		this.gender = gender;
	}
	public int getSillage() {
		return sillage;
	}
	public void setSillage(int sillage) {
		this.sillage = sillage;
	}
	public int getLongevity() {
		return longevity;
	}
	public void setLongevity(int longevity) {
		this.longevity = longevity;
	}

}
