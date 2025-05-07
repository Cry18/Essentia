package com.essentia.EssentiaCatalog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.essentia.EssentiaCatalog.entity.Parfumer;

@Repository
public interface ParfumerRepository extends CrudRepository<Parfumer,Integer> {
    Parfumer findById(int id);
	Parfumer findByName(String name);
    @Override
    List<Parfumer> findAll();

	@Query("SELECT p FROM Parfumer p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Parfumer> findLikeName(@Param("name") String name);
}
