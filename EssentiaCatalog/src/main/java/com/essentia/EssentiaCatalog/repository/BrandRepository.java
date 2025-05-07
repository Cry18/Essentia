package com.essentia.EssentiaCatalog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.essentia.EssentiaCatalog.entity.Brand;

@Repository
public interface BrandRepository extends CrudRepository<Brand,Integer>{
	Brand findById(int id);
	Brand findByName(String name);
        @Override
    List<Brand> findAll();

	@Query("SELECT b FROM Brand b WHERE LOWER(b.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Brand> findLikeName(@Param("name") String name);
}
