package com.essentia.essentiaadministration.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.essentia.essentiaadministration.entity.PerfumePrfNotes;
@Repository
public interface PerfumePrfNotesRepository extends CrudRepository<PerfumePrfNotes,Integer>{
    PerfumePrfNotes findById(int id);
}
