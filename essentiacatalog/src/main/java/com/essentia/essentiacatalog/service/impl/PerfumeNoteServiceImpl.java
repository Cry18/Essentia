package com.essentia.essentiacatalog.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.essentia.common.dto.PerfumeNoteDto;
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
            logger.warn("Perfume note not found with id: {}", id);
            throw new ResourceNotFoundException("PerfumeNote not found");
        }
        PerfumeNoteDto noteDto = new PerfumeNoteDto(note.getName(), note.getDescription());
        noteDto.setId(note.getId());
        noteDto.setImageUrl(note.getImageUrl());
        logger.info("Perfume note with id: {} found", id);
        return noteDto;
    }

    @Override
    public Page<PerfumeNoteDto> findAllPerfumeNotes(Pageable pageable) {
        logger.debug("Fetching all perfume notes - page: {}, size: {}", pageable.getPageNumber(), pageable.getPageSize());
        return perfumeNoteRepository.findAll(pageable)
                .map(n -> {
                    PerfumeNoteDto dto = new PerfumeNoteDto(n.getName(), null);
                    dto.setId(n.getId());
                    dto.setImageUrl(n.getImageUrl());
                    return dto;
                });
    }

    @Override
    public Page<PerfumeNoteDto> findLikeNamePerfumeNotes(String name, Pageable pageable) {
        logger.debug("Fetching perfume notes with '{}' in name", name);
        return perfumeNoteRepository.findLikeName(name, pageable)
                .map(n -> {
                    PerfumeNoteDto dto = new PerfumeNoteDto(n.getName(), null);
                    dto.setId(n.getId());
                    dto.setImageUrl(n.getImageUrl());
                    return dto;
                });
    }
}
