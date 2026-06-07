package com.essentia.essentiauser.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.essentia.essentiauser.entity.Brand;

@Repository
public interface BrandRepository extends CrudRepository<Brand,Integer>{
	Brand findById(int id);
	Brand findByName(String name);
        @Override
    List<Brand> findAll();
}
