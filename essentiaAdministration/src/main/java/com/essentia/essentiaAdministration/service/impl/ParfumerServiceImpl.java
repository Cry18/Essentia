package com.essentia.essentiaadministration.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.essentia.essentiaadministration.dto.ParfumerDto;
import com.essentia.essentiaadministration.entity.Parfumer;
import com.essentia.essentiaadministration.exception.ResourceNotFoundException;
import com.essentia.essentiaadministration.repository.ParfumerRepository;
import com.essentia.essentiaadministration.service.ParfumerService;
@Service
public class ParfumerServiceImpl implements ParfumerService {

    private static final Logger logger = LogManager.getLogger(ParfumerServiceImpl.class);
    
    @Autowired
    private ParfumerRepository parfumerRepository;


    @Override  
    public ParfumerDto create(ParfumerDto p) {
        logger.debug("Creating new parfumer with name: {}",p.getName());
        Parfumer parfumerNew = new Parfumer(
                p.getName(),
                p.getDescription(),
                p.getNazionality());
        parfumerRepository.save(parfumerNew); 
        logger.info("Parfumer with name: {} created", p.getName());
        p.setId(parfumerNew.getId());
        return p;
    }

    @Override
    public ParfumerDto updateparfumer(int id, ParfumerDto p) {
        logger.debug("Fetching parfumer with id: {}",id);
        Parfumer parfumer = parfumerRepository.findById(id);
        if(parfumer == null){
			logger.warn("Parfumer not found with id: {}",id);
			throw new ResourceNotFoundException("Parfumer not found");
		}
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
        logger.info("Parfumer with id: {} updated",id);
        p.setId(id);
        return p;
    }

    @Override
    public ParfumerDto deleteById(int id) { 
        logger.debug("Fetching parfumer with id: {}",id);   
        Parfumer parfumer = parfumerRepository.findById(id);
        if (parfumer != null) {
            parfumerRepository.delete(parfumer);
            logger.info("Parfumer with id: {} deleted",id);
        }else {
            logger.warn("Parfumer with id: {} not found",id);
            throw new ResourceNotFoundException("Parfumer not found with id: " + id);}
        ParfumerDto ParfumerDto = new ParfumerDto(parfumer.getName(), parfumer.getDescription(), parfumer.getNazionality());
        ParfumerDto.setId(id);
        return ParfumerDto;
}
}
