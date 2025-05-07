package com.essentia.essentiaUser.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.essentia.essentiaUser.entity.Perfume;

public interface PerfumeRepository extends CrudRepository<Perfume, Integer> {
    Perfume findById(int id);
    @Override
    List<Perfume> findAll();
}
