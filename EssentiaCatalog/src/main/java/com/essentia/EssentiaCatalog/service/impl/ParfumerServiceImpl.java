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
        return parfumerDto;
    }

    @Override
    public List<String> findAllParfumers() {
        
        List<Parfumer> parfumers = parfumerRepository.findAll();
        List<String> parfumerNames = new ArrayList<>();
        for (Parfumer parfumer : parfumers) {
            parfumerNames.add(parfumer.getName());
        }
        return parfumerNames;
    }

    @Override
    public List<String> findLikeNameParfumers(String name) {
        List<String> parfumerNames = parfumerRepository.findLikeName(name);
        return parfumerNames;
    }
}

