package com.essentia.EssentiaCatalog.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.essentia.EssentiaCatalog.dto.PerfumeDto;
import com.essentia.EssentiaCatalog.dto.PerfumePrfNotesDto;
import com.essentia.EssentiaCatalog.dto.ReviewDto;
import com.essentia.EssentiaCatalog.entity.Perfume;
import com.essentia.EssentiaCatalog.entity.PerfumePrfNotes;
import com.essentia.EssentiaCatalog.entity.Review;
import com.essentia.EssentiaCatalog.exception.ResourceNotFoundException;
import com.essentia.EssentiaCatalog.repository.PerfumePrfNotesRepository;
import com.essentia.EssentiaCatalog.repository.PerfumeRepository;
import com.essentia.EssentiaCatalog.repository.ReviewRepository;
import com.essentia.EssentiaCatalog.service.PerfumeService;
@Service
public class PerfumeServiceImpl implements PerfumeService {

    @Autowired
    private PerfumeRepository perfumeRepository;
    
    @Autowired
    private PerfumePrfNotesRepository perfumePrfNotesRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public PerfumeDto details(int id) {
        Perfume perfume = perfumeRepository.findById(id);
        if (perfume == null) {
            throw new ResourceNotFoundException("Perfume not found with id: " + id);
        }
        List<PerfumePrfNotes> notes = perfumePrfNotesRepository.findByPerfumeId(id);
        List<PerfumePrfNotesDto> notesDto = new ArrayList<>();
        for (PerfumePrfNotes note : notes) {
            notesDto.add(new PerfumePrfNotesDto(note.getNote().getName(), note.getType()));
        }
        List<String> parfumers = perfumeRepository.findParfumersByPerfumeId(id);

        List<Review> reviews = reviewRepository.findByPerfumeId(id);
        List<ReviewDto> reviewDto = new ArrayList<>();
        for (Review review : reviews) {
            String userName = review.getUser().getName();
            reviewDto.add(new ReviewDto(userName, review.getTitle(), review.getDescription(), review.getVote(), review.getSeasonality(), review.isGender(), review.getSillage(), review.getLongevity()));
        }
        PerfumeDto perfumeDto = new PerfumeDto(perfume.getName(), perfume.getBrand().getName(), perfume.getDescription(), notesDto, parfumers, reviewDto);
        perfumeDto.setId(perfume.getId());
        return perfumeDto;
    }

    @Override
    public List<PerfumeDto> findAllPerfumes() {
        List<Perfume> perfumes = perfumeRepository.findAll();
        List<PerfumeDto> perfumeDtos = new ArrayList<>();
        for (Perfume perfume : perfumes) {
            PerfumeDto p = new PerfumeDto(perfume.getName(),perfume.getBrand().getName(),null,null,null,null);
            p.setId(perfume.getId());
            perfumeDtos.add(p);
        }
        return perfumeDtos;
    }

    @Override
    public List<PerfumeDto> findPerfumesByFilters(String name, String parfumer, String brand, String note) {
        List<Perfume> perfumes = perfumeRepository.findByFilters(name, parfumer, brand, note);
        List<PerfumeDto> perfumeDtos = new ArrayList<>();
        for (Perfume perfume : perfumes) {
            PerfumeDto p = new PerfumeDto(perfume.getName(),perfume.getBrand().getName(),null,null,null,null);
            p.setId(perfume.getId());
            perfumeDtos.add(p);
        }
        return perfumeDtos;
    }
    
}
