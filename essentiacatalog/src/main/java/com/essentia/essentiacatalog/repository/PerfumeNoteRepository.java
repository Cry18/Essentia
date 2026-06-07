package com.essentia.essentiacatalog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.essentia.essentiacatalog.entity.PerfumeNote;

@Repository
public interface PerfumeNoteRepository extends JpaRepository<PerfumeNote, Integer> {

    PerfumeNote findById(int id);
    PerfumeNote findByName(String name);

    @Query("SELECT p FROM PerfumeNote p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<PerfumeNote> findLikeName(@Param("name") String name, Pageable pageable);
}
