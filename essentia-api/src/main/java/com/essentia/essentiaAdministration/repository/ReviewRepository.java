package com.essentia.essentiaAdministration.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.essentia.essentiaAdministration.entity.Review;
@Repository
public interface ReviewRepository extends CrudRepository<Review,Integer>{
	Review findById(int id);
}