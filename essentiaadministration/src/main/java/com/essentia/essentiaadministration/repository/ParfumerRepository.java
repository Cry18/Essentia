package com.essentia.essentiaadministration.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.essentia.essentiaadministration.entity.Parfumer;
@Repository
public interface ParfumerRepository extends CrudRepository<Parfumer,Integer>{
	Parfumer findById(int id);
	Parfumer findByName(String name);

	@Query(value = "SELECT COUNT(*) FROM perfume_parfumer WHERE parfumer = :parfumerId", nativeQuery = true)
	long countPerfumesByParfumerId(@Param("parfumerId") int parfumerId);
}