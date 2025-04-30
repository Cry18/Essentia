package com.essentia.essentiaAdministration.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.essentia.essentiaAdministration.entity.Brand;
@Repository
public interface BrandRepository extends CrudRepository<Brand,Integer>{
	Brand findById(int id);
	Brand findByName(String name);
}