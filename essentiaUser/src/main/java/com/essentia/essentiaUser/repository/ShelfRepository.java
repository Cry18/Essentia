package com.essentia.essentiauser.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.essentia.essentiauser.entity.Shelf;

@Repository
public interface ShelfRepository extends CrudRepository<Shelf,Integer>{
	Shelf findById(int id);
	Shelf findByName(String name);

    @Query("SELECT s FROM Shelf s LEFT JOIN FETCH s.perfumes WHERE s.id = :id")
    Shelf findByIdWithPerfumes(@Param("id") int id);
}

