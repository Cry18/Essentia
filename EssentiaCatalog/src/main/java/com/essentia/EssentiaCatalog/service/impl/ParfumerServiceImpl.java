package com.essentia.EssentiaCatalog.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.essentia.EssentiaCatalog.dto.ParfumerDto;
import com.essentia.EssentiaCatalog.entity.Parfumer;
import com.essentia.EssentiaCatalog.exception.ResourceNotFoundException;
import com.essentia.EssentiaCatalog.repository.ParfumerRepository;
import com.essentia.EssentiaCatalog.service.ParfumerService;
@Service
public class ParfumerServiceImpl implements ParfumerService {

    @Autowired
    private ParfumerRepository parfumerRepository;

    @Override
    public ParfumerDto details(int id) {
        Parfumer parfumer = parfumerRepository.findById(id);
        if (parfumer == null) {
            throw new ResourceNotFoundException("Parfumer not found with id: " + id);
        }
        ParfumerDto parfumerDto = new ParfumerDto(parfumer.getName(), parfumer.getDescription(), parfumer.getNazionality());
        parfumerDto.setId(parfumer.getId());
        return parfumerDto;
    }

    @Override
    public List<ParfumerDto> findAllParfumers() {
        
        List<Parfumer> parfumers = parfumerRepository.findAll();
        List<ParfumerDto> parfumerDtos = new ArrayList<>();
        for (Parfumer parfumer : parfumers) {
            ParfumerDto p =  new ParfumerDto(parfumer.getName(),null,null);
            p.setId(parfumer.getId());
            parfumerDtos.add(p);
        }
        return parfumerDtos;
    }

    @Override
    public List<ParfumerDto> findLikeNameParfumers(String name) {
        List<Parfumer> parfumers = parfumerRepository.findLikeName(name);
        List<ParfumerDto> parfumerDtos = new ArrayList<>();
        for (Parfumer parfumer : parfumers) {
            ParfumerDto p =  new ParfumerDto(parfumer.getName(),null,null);
            p.setId(parfumer.getId());
            parfumerDtos.add(p);
        }
        return parfumerDtos;
    }
}

