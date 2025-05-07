package com.essentia.EssentiaCatalog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.essentia.EssentiaCatalog.entity.PerfumeNote;

@Repository
public interface PerfumeNoteRepository extends CrudRepository<PerfumeNote, Integer> {
    PerfumeNote findById(int id);
	PerfumeNote findByName(String name);
    @Override
    List<PerfumeNote> findAll();

	@Query("SELECT p FROM PerfumeNote p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<PerfumeNote> findLikeName(@Param("name") String name);
}