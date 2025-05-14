package com.essentia.essentiacatalog.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.essentia.essentiacatalog.dto.PerfumeNoteDto;
import com.essentia.essentiacatalog.entity.PerfumeNote;
import com.essentia.essentiacatalog.exception.ResourceNotFoundException;
import com.essentia.essentiacatalog.repository.PerfumeNoteRepository;
import com.essentia.essentiacatalog.service.PerfumeNoteService;

@Service
public class PerfumeNoteServiceImpl implements PerfumeNoteService {

    private static final Logger logger = LogManager.getLogger(PerfumeNoteServiceImpl.class);
    
    @Autowired
    private PerfumeNoteRepository perfumeNoteRepository;

    @Override
    public PerfumeNoteDto details(int id) {
        logger.debug("Fetching perfume note with id: {}", id);
        PerfumeNote note = perfumeNoteRepository.findById(id);
        if (note == null) {
            logger.warn("Perfume note not found with id: {}",id);
            throw new ResourceNotFoundException("PerfumeNote not found");
        }
        PerfumeNoteDto noteDto = new PerfumeNoteDto(note.getName(), note.getDescription());
        noteDto.setId(note.getId());
        logger.info("Perfume note with id: {} found",id);
        return noteDto;
    }

    @Override
    public List<PerfumeNoteDto> findAllPerfumeNotes() {
        logger.debug("Fetching all perfume notes");
        
        List<PerfumeNote> notes = perfumeNoteRepository.findAll();
        List<PerfumeNoteDto> perfumeNoteDtos = new ArrayList<>();
        for (PerfumeNote note : notes) {
            PerfumeNoteDto p =  new PerfumeNoteDto(note.getName(),null);
            p.setId(note.getId());
            perfumeNoteDtos.add(p);
        }
        logger.info("Perfume notes found");
        return perfumeNoteDtos;
    }

    @Override
    public List<PerfumeNoteDto> findLikeNamePerfumeNotes(String name) {
        logger.debug("Fetching perfume note with: {} in name", name);
        List<PerfumeNote> notes = perfumeNoteRepository.findLikeName(name);
        List<PerfumeNoteDto> perfumeNoteDtos = new ArrayList<>();
        for (PerfumeNote note : notes) {
            PerfumeNoteDto p =  new PerfumeNoteDto(note.getName(),null);
            p.setId(note.getId());
            perfumeNoteDtos.add(p);
        }
        logger.info("Perfume note with id: {} found",note.getId());
        return perfumeNoteDtos;
    }
}
