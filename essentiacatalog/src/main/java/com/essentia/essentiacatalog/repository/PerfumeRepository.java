package com.essentia.essentiacatalog.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.essentia.essentiacatalog.entity.Perfume;

public interface PerfumeRepository extends JpaRepository<Perfume, Integer> {

    Perfume findById(int id);

    /**
     * Filters perfumes by name, brand, parfumer and note (all optional, case-insensitive).
     * A separate countQuery is required because DISTINCT + JOIN can confuse
     * Spring Data's auto-generated count query.
     */
    @Query(value =
        "SELECT DISTINCT p FROM Perfume p " +
        "LEFT JOIN p.brand b " +
        "LEFT JOIN p.parfumers par " +
        "LEFT JOIN PerfumePrfNotes ppn ON p.id = ppn.perfume.id " +
        "LEFT JOIN ppn.note n " +
        "WHERE (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
        "AND (:brand IS NULL OR LOWER(b.name) LIKE LOWER(CONCAT('%', :brand, '%'))) " +
        "AND (:parfumer IS NULL OR LOWER(par.name) LIKE LOWER(CONCAT('%', :parfumer, '%'))) " +
        "AND (:note IS NULL OR LOWER(n.name) LIKE LOWER(CONCAT('%', :note, '%')))",
        countQuery =
        "SELECT COUNT(DISTINCT p) FROM Perfume p " +
        "LEFT JOIN p.brand b " +
        "LEFT JOIN p.parfumers par " +
        "LEFT JOIN PerfumePrfNotes ppn ON p.id = ppn.perfume.id " +
        "LEFT JOIN ppn.note n " +
        "WHERE (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
        "AND (:brand IS NULL OR LOWER(b.name) LIKE LOWER(CONCAT('%', :brand, '%'))) " +
        "AND (:parfumer IS NULL OR LOWER(par.name) LIKE LOWER(CONCAT('%', :parfumer, '%'))) " +
        "AND (:note IS NULL OR LOWER(n.name) LIKE LOWER(CONCAT('%', :note, '%')))")
    Page<Perfume> findByFilters(@Param("name") String name,
                                @Param("parfumer") String parfumer,
                                @Param("brand") String brand,
                                @Param("note") String note,
                                Pageable pageable);

    @Query("SELECT p.name FROM Parfumer p JOIN p.perfumes pf WHERE pf.id = :perfumeId")
    List<String> findParfumersByPerfumeId(@Param("perfumeId") int perfumeId);
}
