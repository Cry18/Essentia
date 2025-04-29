package com.essentia.essentia_api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.essentia.essentia_api.entity.Parfumer;
@Repository
public interface ParfumerRepository extends CrudRepository<Parfumer,Integer>{
	Parfumer findById(int id);
	Parfumer findByName(String name);
}