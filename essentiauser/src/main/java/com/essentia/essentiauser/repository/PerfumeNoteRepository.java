package com.essentia.essentiauser.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.essentia.essentiauser.entity.PerfumeNote;

@Repository
public interface PerfumeNoteRepository extends CrudRepository<PerfumeNote, Integer> {
    PerfumeNote findById(int id);
	PerfumeNote findByName(String name);
}