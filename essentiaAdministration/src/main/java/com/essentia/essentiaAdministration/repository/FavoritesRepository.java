package com.essentia.essentiaAdministration.repository;
/* 
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoritesRepository {

    // non estende extends JpaRepository<Object, Integer> perchè esegue solo 1 query nativa

    @Query("SELECT f.perfume.name FROM Favorites f GROUP BY f.perfume.name ORDER BY COUNT(f.perfume) DESC")
    List<String> findMostDesiredPerfumes();
}*/