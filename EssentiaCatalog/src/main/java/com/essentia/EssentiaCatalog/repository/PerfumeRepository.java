package com.essentia.EssentiaCatalog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.essentia.EssentiaCatalog.entity.Perfume;

public interface PerfumeRepository extends CrudRepository<Perfume, Integer> {
    Perfume findById(int id);
    @Override
    List<Perfume> findAll();

    @Query("SELECT DISTINCT p.name FROM Perfume p " +
    "LEFT JOIN p.brand b " +
    "LEFT JOIN p.parfumers par " +
    "LEFT JOIN PerfumePrfNotes ppn ON p.id = ppn.perfume.id " +
    "LEFT JOIN ppn.note n " +
    "WHERE (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
    "AND (:brand IS NULL OR LOWER(b.name) LIKE LOWER(CONCAT('%', :brand, '%'))) " +
    "AND (:parfumer IS NULL OR LOWER(par.name) LIKE LOWER(CONCAT('%', :parfumer, '%'))) " +
    "AND (:note IS NULL OR LOWER(n.name) LIKE LOWER(CONCAT('%', :note, '%')))")
List<String> findByFilters(@Param("name") String name,
                        @Param("parfumer") String parfumer,
                        @Param("brand") String brand,
                        @Param("note") String note);

    @Query("SELECT p.name FROM Parfumer p JOIN p.perfumes pf WHERE pf.id = :perfumeId")
    List<String> findParfumersByPerfumeId(@Param("perfumeId") int perfumeId);
}
