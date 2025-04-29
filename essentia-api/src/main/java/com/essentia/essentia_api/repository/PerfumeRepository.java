package com.essentia.essentia_api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.essentia.essentia_api.entity.Perfume;
@Repository
public interface PerfumeRepository extends CrudRepository<Perfume,Integer>{
	Perfume findById(int id);
	Perfume findByName(String name);
}