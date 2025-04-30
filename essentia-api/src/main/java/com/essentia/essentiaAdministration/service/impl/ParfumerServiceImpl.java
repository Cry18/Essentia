package com.essentia.essentiaAdministration.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.essentia.essentiaAdministration.dto.ParfumerDto;
import com.essentia.essentiaAdministration.entity.Parfumer;
import com.essentia.essentiaAdministration.repository.ParfumerRepository;
import com.essentia.essentiaAdministration.service.ParfumerService;
@Service
public class ParfumerServiceImpl implements ParfumerService {
    
    @Autowired
    private ParfumerRepository parfumerRepository;
    
    @Override
    public List<ParfumerDto> findAll() {
        List<Parfumer> parfumer = (List<Parfumer>) parfumerRepository.findAll();
        List<ParfumerDto> parfumerDto = parfumer.stream()
                .map(p -> new ParfumerDto(p.getName(), p.getDescription(), p.getNazionality()))
                .toList();
        return parfumerDto;
    }

    @Override  
    public void create(ParfumerDto p) {
        Parfumer parfumerNew = new Parfumer(
                p.getName(),
                p.getDescription(),
                p.getNazionality());
        
        parfumerRepository.save(parfumerNew); 
    }

    @Override
    public void updateparfumer(int id, ParfumerDto p) {
        Parfumer parfumer = parfumerRepository.findById(id);	
        // Aggiorna solo i campi forniti
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
    }

    @Override
    public void deleteById(int id) {    
        Parfumer parfumer = parfumerRepository.findById(id);
        if (parfumer != null) {
            parfumerRepository.delete(parfumer);
        }
}
}
