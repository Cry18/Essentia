package com.essentia.essentiacatalog.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.essentia.essentiacatalog.entity.Review;
@Repository
public interface ReviewRepository extends CrudRepository<Review, Integer> {
    List<Review> findByPerfumeId(int perfumeId);

}