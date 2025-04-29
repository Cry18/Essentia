package com.essentia.essentia_api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.essentia.essentia_api.entity.Review;
@Repository
public interface ReviewRepository extends CrudRepository<Review,Integer>{
	Review findById(int id);
}