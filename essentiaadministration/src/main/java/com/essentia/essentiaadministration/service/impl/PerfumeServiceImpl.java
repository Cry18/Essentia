package com.essentia.essentiaadministration.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.essentia.essentiaadministration.dto.PerfumeDto;
import com.essentia.essentiaadministration.entity.Brand;
import com.essentia.essentiaadministration.entity.Parfumer;
import com.essentia.essentiaadministration.entity.Perfume;
import com.essentia.essentiaadministration.entity.PerfumeNote;
import com.essentia.essentiaadministration.entity.PerfumePrfNotes;
import com.essentia.essentiaadministration.exception.ResourceNotFoundException;
import com.essentia.essentiaadministration.repository.BrandRepository;
import com.essentia.essentiaadministration.repository.ParfumerRepository;
import com.essentia.essentiaadministration.repository.PerfumeNoteRepository;
import com.essentia.essentiaadministration.repository.PerfumePrfNotesRepository;
import com.essentia.essentiaadministration.repository.PerfumeRepository;
import com.essentia.essentiaadministration.repository.ReviewRepository;
import com.essentia.essentiaadministration.service.PerfumeService;

import jakarta.transaction.Transactional;
@Service
public class PerfumeServiceImpl implements PerfumeService {

    private static final Logger logger = LogManager.getLogger(PerfumeServiceImpl.class);

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private PerfumeRepository perfumeRepository;
    
    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ParfumerRepository parfumerRepository;

    @Autowired
    private PerfumeNoteRepository perfumeNoteRepository;

    @Autowired
    private PerfumePrfNotesRepository perfumePrfNoteRepository;
  

    @Override
    @Transactional
    public PerfumeDto create(PerfumeDto perfume) {

        logger.debug("Fetching brand with id: {}",perfume.getBrand());
        Brand brand = brandRepository.findById(perfume.getBrand());
        if (brand == null){
            logger.warn("Brand not found with id: {}",perfume.getBrand());
            throw new ResourceNotFoundException("Brand not found");
        }

        //crea una lista di profumieri
        List<Parfumer> parfumers = new ArrayList<>();
        //popola la lista a partire dalla lista di nomi dei profumieri del dto
        for (int i = 0; i < perfume.getParfumers().size(); i++) {
            int parfumerId = perfume.getParfumers().get(i);
            Parfumer parfumer = parfumerRepository.findById(parfumerId);
            if (parfumer != null) {
                parfumers.add(parfumer);
            } else {
                logger.warn("Parfumer not found with id: {}",parfumerId);
                throw new ResourceNotFoundException("Parfumer not found");
            }
        }

        Perfume newPerfume = new Perfume(
                perfume.getName(),
                perfume.getDescription(),
                brand,
                parfumers
        );
        newPerfume.setImageUrl(perfume.getImageUrl());
        perfumeRepository.save(newPerfume);
        logger.info("New perfume with name: {} created",perfume.getName());

        //popola la tabella ponte PerfumePrfNotes
        logger.debug("Adding perfume notes");
        for (int i = 0; i < perfume.getNotes().size(); i++) {
            PerfumeNote Note = perfumeNoteRepository.findById(perfume.getNotes().get(i).getNoteId());
            if (Note == null) {
                throw new ResourceNotFoundException("Note not found: " + perfume.getNotes().get(i).getNoteId());
            }
            PerfumePrfNotes perfumeNote = new PerfumePrfNotes(
                    newPerfume,
                    Note,
                    perfume.getNotes().get(i).getType()
            );
            perfumePrfNoteRepository.save(perfumeNote);
            }
            logger.info("Notes for perfume with name: {}, linked",perfume.getName());
            perfume.setId(newPerfume.getId());
            return perfume;
        }
    

    @Override
    @Transactional
    public PerfumeDto updatePerfume(int id, PerfumeDto perfume) {
        logger.debug("Fetching perfume with id: {}", id);
        Perfume perfumeUpdated = perfumeRepository.findById(id);
        if (perfumeUpdated == null) {
            logger.warn("Perfume not found with id: {}", id);
            throw new ResourceNotFoundException("Perfume not found");
        }

        // Update name and description
        perfumeUpdated.setName(perfume.getName());
        perfumeUpdated.setDescription(perfume.getDescription());
        if (perfume.getImageUrl() != null) {
            perfumeUpdated.setImageUrl(perfume.getImageUrl());
        }

        // Update brand
        logger.debug("Fetching brand with id: {}", perfume.getBrand());
        Brand brand = brandRepository.findById(perfume.getBrand());
        if (brand == null) {
            logger.warn("Brand not found with id: {}", perfume.getBrand());
            throw new ResourceNotFoundException("Brand not found");
        }
        perfumeUpdated.setBrand(brand);

        // Update parfumers
        logger.debug("Fetching parfumers");
        List<Parfumer> parfumers = new ArrayList<>();
        for (int parfumerId : perfume.getParfumers()) {
            Parfumer parfumer = parfumerRepository.findById(parfumerId);
            if (parfumer == null) {
                logger.warn("Parfumer not found with id: {}", parfumerId);
                throw new ResourceNotFoundException("Parfumer not found");
            }
            parfumers.add(parfumer);
        }
        perfumeUpdated.setParfumers(parfumers);
        perfumeRepository.save(perfumeUpdated);
        logger.info("Perfume with id: {} updated", id);

        // Update notes: delete existing ones first, then insert new ones
        logger.debug("Replacing perfume notes");
        perfumePrfNoteRepository.deleteByPerfume(perfumeUpdated);
        for (int i = 0; i < perfume.getNotes().size(); i++) {
            PerfumeNote note = perfumeNoteRepository.findById(perfume.getNotes().get(i).getNoteId());
            if (note == null) {
                logger.warn("Note not found with id: {}", perfume.getNotes().get(i).getNoteId());
                throw new ResourceNotFoundException("Note not found: " + perfume.getNotes().get(i).getNoteId());
            }
            perfumePrfNoteRepository.save(new PerfumePrfNotes(perfumeUpdated, note, perfume.getNotes().get(i).getType()));
        }
        logger.info("Notes for perfume with id: {} updated", id);

        perfume.setId(id);
        return perfume;
    }

    @Override
    @Transactional
    public PerfumeDto deleteById(int id) {
        logger.debug("Fetching perfume with id: {}",id);
        Perfume perfume = perfumeRepository.findById(id);
        
        if (perfume == null) {
        logger.error("Perfume not found with id: {}",id);
        throw new ResourceNotFoundException("Perfume not found");
        }

        PerfumeDto perfumeDto = new PerfumeDto();
        perfumeDto.setName(perfume.getName());
        perfumeDto.setBrand(perfume.getBrand().getId());
        perfumeDto.setId(id);

		perfumeRepository.delete(perfume);
        logger.info("Perfume with id: {} deleted",id);

        return perfumeDto;
    }
    
    @Override
    public PerfumeDto findMostDesiredPerfume() {
        logger.debug("Fetching most desired perfume");
        Perfume mostDesiredPerfume = perfumeRepository.findMostDesiredPerfume();
        if (mostDesiredPerfume != null) {
            PerfumeDto perfumeDto = new PerfumeDto();
            perfumeDto.setName(mostDesiredPerfume.getName());
            perfumeDto.setBrand(mostDesiredPerfume.getBrand().getId());
            perfumeDto.setId(mostDesiredPerfume.getId());
            return perfumeDto;
        } else {
            logger.info("No perfume is in any favorites list");
            return null;}
    }

    @Override
    public PerfumeDto findMostAppreciatedPerfume() {
        logger.debug("Fetching most appreciated perfume");
        Perfume mostAppreciatedPerfume = reviewRepository.findMostAppreciatedPerfume();
        if (mostAppreciatedPerfume != null) {
            PerfumeDto perfumeDto = new PerfumeDto();
            perfumeDto.setName(mostAppreciatedPerfume.getName());
            perfumeDto.setBrand(mostAppreciatedPerfume.getBrand().getId());
            perfumeDto.setId(mostAppreciatedPerfume.getId());
            return perfumeDto;
        } else {
            logger.info("No perfume has a rating of 5 stars");
            return null;}
    }
}
