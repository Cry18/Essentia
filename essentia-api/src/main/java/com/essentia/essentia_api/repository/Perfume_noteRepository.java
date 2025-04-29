package com.essentia.essentia_api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.essentia.essentia_api.entity.Perfume_note;
@Repository
public interface Perfume_noteRepository extends CrudRepository<Perfume_note,Integer>{
	Perfume_note findById(int id);
	Perfume_note findByName(String name);
}