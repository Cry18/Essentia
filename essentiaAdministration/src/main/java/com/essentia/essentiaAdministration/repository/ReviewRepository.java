package com.essentia.essentiaAdministration.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.essentia.essentiaAdministration.entity.Perfume;
import com.essentia.essentiaAdministration.entity.Review;
@Repository
public interface ReviewRepository extends CrudRepository<Review,Integer>{
	Review findById(int id);
	@Query("SELECT r.perfume FROM Review r WHERE r.vote = 5 GROUP BY r.perfume ORDER BY COUNT(r.vote) DESC")
	Perfume findMostAppreciatedPerfume();
}