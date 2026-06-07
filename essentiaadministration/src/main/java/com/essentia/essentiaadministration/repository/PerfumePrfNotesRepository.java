package com.essentia.essentiaadministration.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.essentia.essentiaadministration.entity.PerfumePrfNotes;
@Repository
public interface PerfumePrfNotesRepository extends CrudRepository<PerfumePrfNotes,Integer>{
    PerfumePrfNotes findById(int id);
    // Spring Data auto-generates: DELETE FROM perfume_prfnotes WHERE perfume = ?
    void deleteByPerfume(com.essentia.essentiaadministration.entity.Perfume perfume);

    @Query("SELECT COUNT(p) FROM PerfumePrfNotes p WHERE p.note.id = :noteId")
    long countByNoteId(@Param("noteId") int noteId);
}
