package com.essentia.essentiaadministration.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.essentia.essentiaadministration.entity.Perfume;
@Repository
public interface PerfumeRepository extends CrudRepository<Perfume,Integer>{
	Perfume findById(int id);
	Perfume findByName(String name);
	 @Query(value = """
        SELECT p.*
        FROM favorites f
        JOIN perfume p ON f.perfume = p.id
        GROUP BY p.id
        ORDER BY COUNT(f.user) DESC
        LIMIT 1
        """, nativeQuery = true)
    Perfume findMostDesiredPerfume();
}