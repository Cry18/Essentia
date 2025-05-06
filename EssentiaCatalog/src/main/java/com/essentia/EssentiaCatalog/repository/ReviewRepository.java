package com.essentia.EssentiaCatalog.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.essentia.EssentiaCatalog.entity.Review;
@Repository
public interface ReviewRepository extends CrudRepository<Review, Integer> {
    List<Review> findByPerfumeId(int perfumeId);

}