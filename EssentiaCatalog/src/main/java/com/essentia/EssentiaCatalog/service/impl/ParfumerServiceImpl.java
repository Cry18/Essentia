package com.essentia.essentiacatalog.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.essentia.essentiacatalog.dto.ParfumerDto;
import com.essentia.essentiacatalog.entity.Parfumer;
import com.essentia.essentiacatalog.exception.ResourceNotFoundException;
import com.essentia.essentiacatalog.repository.ParfumerRepository;
import com.essentia.essentiacatalog.service.ParfumerService;
@Service
public class ParfumerServiceImpl implements ParfumerService {

    private static final Logger logger = LogManager.getLogger(ParfumerServiceImpl.class);


    @Autowired
    private ParfumerRepository parfumerRepository;

    @Override
    public ParfumerDto details(int id) {
        logger.debug("Fetching parfumer with id: {}", id);
        Parfumer parfumer = parfumerRepository.findById(id);
        if (parfumer == null) {
            logger.warn("Parfumer not found with id: {}",id);
            throw new ResourceNotFoundException("Parfumer not found");
        }
        ParfumerDto parfumerDto = new ParfumerDto(parfumer.getName(), parfumer.getDescription(), parfumer.getNazionality());
        parfumerDto.setId(parfumer.getId());
        logger.info("Parfumer with id: {} found",id);
        return parfumerDto;
    }

    @Override
    public List<ParfumerDto> findAllParfumers() {
        
        logger.debug("Fetching all parfumers");
        List<Parfumer> parfumers = parfumerRepository.findAll();
        List<ParfumerDto> parfumerDtos = new ArrayList<>();
        for (Parfumer parfumer : parfumers) {
            ParfumerDto p =  new ParfumerDto(parfumer.getName(),null,null);
            p.setId(parfumer.getId());
            parfumerDtos.add(p);
        }
        logger.info("Parfumers found");
        return parfumerDtos;
    }

    @Override
    public List<ParfumerDto> findLikeNameParfumers(String name) {
        logger.debug("Fetching parfumers with: {} in name", name);
        List<Parfumer> parfumers = parfumerRepository.findLikeName(name);
        List<ParfumerDto> parfumerDtos = new ArrayList<>();
        for (Parfumer parfumer : parfumers) {
            ParfumerDto p =  new ParfumerDto(parfumer.getName(),null,null);
            p.setId(parfumer.getId());
            parfumerDtos.add(p);
        }
        logger.info("Parfumer with id: {} found",parfumer.getId());
        return parfumerDtos;
    }
}

