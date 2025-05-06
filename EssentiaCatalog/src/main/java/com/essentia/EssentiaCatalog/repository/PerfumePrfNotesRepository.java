package com.essentia.EssentiaCatalog.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.essentia.EssentiaCatalog.entity.PerfumePrfNotes;

public interface PerfumePrfNotesRepository extends CrudRepository<PerfumePrfNotes, Integer> {
    PerfumePrfNotes findById(int id);
    
    @Override
    List<PerfumePrfNotes> findAll();

    List<PerfumePrfNotes> findByPerfumeId(int perfumeId);
    
}
