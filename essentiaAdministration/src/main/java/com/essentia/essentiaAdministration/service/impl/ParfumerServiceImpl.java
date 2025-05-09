package com.essentia.essentiaadministration.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.essentia.essentiaadministration.dto.ParfumerDto;
import com.essentia.essentiaadministration.entity.Parfumer;
import com.essentia.essentiaadministration.exception.ResourceNotFoundException;
import com.essentia.essentiaadministration.repository.ParfumerRepository;
import com.essentia.essentiaadministration.service.ParfumerService;
@Service
public class ParfumerServiceImpl implements ParfumerService {
    
    @Autowired
    private ParfumerRepository parfumerRepository;


    @Override  
    public ParfumerDto create(ParfumerDto p) {
        Parfumer parfumerNew = new Parfumer(
                p.getName(),
                p.getDescription(),
                p.getNazionality());
        
        parfumerRepository.save(parfumerNew); 
        p.setId(parfumerNew.getId());
        return p;
    }

    @Override
    public ParfumerDto updateparfumer(int id, ParfumerDto p) {
        Parfumer parfumer = parfumerRepository.findById(id);	
        if (p.getName() != null) {
            parfumer.setName(p.getName());
        }
        if (p.getDescription() != null) {
            parfumer.setDescription(p.getDescription());
        }
        if (p.getNazionality() != null) {
            parfumer.setNazionality(p.getNazionality());
        }
    
        parfumerRepository.save(parfumer);
        p.setId(id);
        return p;
    }

    @Override
    public ParfumerDto deleteById(int id) {    
        Parfumer parfumer = parfumerRepository.findById(id);
        if (parfumer != null) {
            parfumerRepository.delete(parfumer);
        }else throw new ResourceNotFoundException("Parfumer not found with id: " + id);
        ParfumerDto ParfumerDto = new ParfumerDto(parfumer.getName(), parfumer.getDescription(), parfumer.getNazionality());
        ParfumerDto.setId(id);
        return ParfumerDto;
}
}
