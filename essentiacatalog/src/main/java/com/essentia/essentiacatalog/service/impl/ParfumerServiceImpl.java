package com.essentia.essentiacatalog.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.essentia.common.dto.ParfumerDto;
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
            logger.warn("Parfumer not found with id: {}", id);
            throw new ResourceNotFoundException("Parfumer not found");
        }
        ParfumerDto parfumerDto = new ParfumerDto(parfumer.getName(), parfumer.getDescription(), parfumer.getNationality());
        parfumerDto.setId(parfumer.getId());
        parfumerDto.setImageUrl(parfumer.getImageUrl());
        logger.info("Parfumer with id: {} found", id);
        return parfumerDto;
    }

    @Override
    public Page<ParfumerDto> findAllParfumers(Pageable pageable) {
        logger.debug("Fetching all parfumers - page: {}, size: {}", pageable.getPageNumber(), pageable.getPageSize());
        return parfumerRepository.findAll(pageable)
                .map(p -> {
                    ParfumerDto dto = new ParfumerDto(p.getName(), null, null);
                    dto.setId(p.getId());
                    dto.setImageUrl(p.getImageUrl());
                    return dto;
                });
    }

    @Override
    public Page<ParfumerDto> findLikeNameParfumers(String name, Pageable pageable) {
        logger.debug("Fetching parfumers with '{}' in name", name);
        return parfumerRepository.findLikeName(name, pageable)
                .map(p -> {
                    ParfumerDto dto = new ParfumerDto(p.getName(), null, null);
                    dto.setId(p.getId());
                    dto.setImageUrl(p.getImageUrl());
                    return dto;
                });
    }
}
