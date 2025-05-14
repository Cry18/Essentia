package com.essentia.essentiacatalog.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.essentia.essentiacatalog.dto.PerfumeDto;
import com.essentia.essentiacatalog.dto.PerfumePrfNotesDto;
import com.essentia.essentiacatalog.dto.ReviewDto;
import com.essentia.essentiacatalog.entity.Perfume;
import com.essentia.essentiacatalog.entity.PerfumePrfNotes;
import com.essentia.essentiacatalog.entity.Review;
import com.essentia.essentiacatalog.exception.ResourceNotFoundException;
import com.essentia.essentiacatalog.repository.PerfumePrfNotesRepository;
import com.essentia.essentiacatalog.repository.PerfumeRepository;
import com.essentia.essentiacatalog.repository.ReviewRepository;
import com.essentia.essentiacatalog.service.PerfumeService;
@Service
public class PerfumeServiceImpl implements PerfumeService {

    private static final Logger logger = LogManager.getLogger(PerfumeServiceImpl.class);

    @Autowired
    private PerfumeRepository perfumeRepository;
    
    @Autowired
    private PerfumePrfNotesRepository perfumePrfNotesRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public PerfumeDto details(int id) {
        logger.debug("Fetching perfume with id: {}" ,id);
        Perfume perfume = perfumeRepository.findById(id);
        if (perfume == null) {
            logger.warn("Perfume not found with id: {}", id);
            throw new ResourceNotFoundException("Perfume not found");
        }
        logger.debug("Fetching perfume notes");
        List<PerfumePrfNotes> notes = perfumePrfNotesRepository.findByPerfumeId(id);
        List<PerfumePrfNotesDto> notesDto = new ArrayList<>();
        for (PerfumePrfNotes note : notes) {
            notesDto.add(new PerfumePrfNotesDto(note.getNote().getName(), note.getType()));
        }
        logger.debug("Fetching parfumers");
        List<String> parfumers = perfumeRepository.findParfumersByPerfumeId(id);

        logger.debug("Fetching reviews");
        List<Review> reviews = reviewRepository.findByPerfumeId(id);
        List<ReviewDto> reviewDto = new ArrayList<>();
        for (Review review : reviews) {
            String userName = review.getUser().getName();
            reviewDto.add(new ReviewDto(userName, review.getTitle(), review.getDescription(), review.getVote(), review.getSeasonality(), review.isGender(), review.getSillage(), review.getLongevity()));
        }
        PerfumeDto perfumeDto = new PerfumeDto(perfume.getName(), perfume.getBrand().getName(), perfume.getDescription(), notesDto, parfumers, reviewDto);
        perfumeDto.setId(perfume.getId());
        logger.info("Perfume with id: {} found",id);
        return perfumeDto;
    }

    @Override
    public List<PerfumeDto> findAllPerfumes() {
        logger.debug("Fetching all perfumes");
        List<Perfume> perfumes = perfumeRepository.findAll();
        List<PerfumeDto> perfumeDtos = new ArrayList<>();
        for (Perfume perfume : perfumes) {
            PerfumeDto p = new PerfumeDto(perfume.getName(),perfume.getBrand().getName(),null,null,null,null);
            p.setId(perfume.getId());
            perfumeDtos.add(p);
        }
        logger.info("Perfumes found");
        return perfumeDtos;
    }

    @Override
    public List<PerfumeDto> findPerfumesByFilters(String name, String parfumer, String brand, String note) {
        logger.debug("Fetching perfume with name: {}, or parfumer: {}, or brand: {}, or note: {}", name, parfumer, brand, note);
        List<Perfume> perfumes = perfumeRepository.findByFilters(name, parfumer, brand, note);
        List<PerfumeDto> perfumeDtos = new ArrayList<>();
        for (Perfume perfume : perfumes) {
            PerfumeDto p = new PerfumeDto(perfume.getName(),perfume.getBrand().getName(),null,null,null,null);
            p.setId(perfume.getId());
            perfumeDtos.add(p);
        }
        logger.info("Perfume with id: {} found",perfume.getId());
        return perfumeDtos;
    }
    
}
