package com.essentia.essentiauser.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.essentia.essentiauser.entity.Parfumer;

@Repository
public interface ParfumerRepository extends CrudRepository<Parfumer,Integer> {
    Parfumer findById(int id);
	Parfumer findByName(String name);
}
