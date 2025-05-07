package com.essentia.essentiaUser.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.essentia.essentiaUser.entity.Shelf;

@Repository
public interface ShelfRepository extends CrudRepository<Shelf,Integer>{
	Shelf findById(int id);
	Shelf findByName(String name);
    @Override
    List<Shelf> findAll();
}
