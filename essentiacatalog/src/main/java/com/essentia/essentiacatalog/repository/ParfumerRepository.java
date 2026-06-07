package com.essentia.essentiacatalog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.essentia.essentiacatalog.entity.Parfumer;

@Repository
public interface ParfumerRepository extends JpaRepository<Parfumer, Integer> {

    Parfumer findById(int id);
    Parfumer findByName(String name);

    @Query("SELECT p FROM Parfumer p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<Parfumer> findLikeName(@Param("name") String name, Pageable pageable);
}
