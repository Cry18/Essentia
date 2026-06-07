package com.essentia.essentiacatalog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.essentia.essentiacatalog.entity.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {

    Brand findById(int id);
    Brand findByName(String name);

    @Query("SELECT b FROM Brand b WHERE LOWER(b.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<Brand> findLikeName(@Param("name") String name, Pageable pageable);
}
