package com.essentia.essentia_api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.essentia.essentia_api.entity.Shelf;
@Repository
public interface ShelfRepository extends CrudRepository<Shelf,Integer>{
	Shelf findById(int id);
	Shelf findByName(String name);
}
