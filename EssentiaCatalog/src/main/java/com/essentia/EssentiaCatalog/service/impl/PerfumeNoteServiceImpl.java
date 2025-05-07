package com.essentia.EssentiaCatalog.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.essentia.EssentiaCatalog.dto.PerfumeNoteDto;
import com.essentia.EssentiaCatalog.entity.PerfumeNote;
import com.essentia.EssentiaCatalog.exception.ResourceNotFoundException;
import com.essentia.EssentiaCatalog.repository.PerfumeNoteRepository;
import com.essentia.EssentiaCatalog.service.PerfumeNoteService;

@Service
public class PerfumeNoteServiceImpl implements PerfumeNoteService {
    
    @Autowired
    private PerfumeNoteRepository perfumeNoteRepository;

    @Override
    public PerfumeNoteDto details(int id) {
        PerfumeNote note = perfumeNoteRepository.findById(id);
        if (note == null) {
            throw new ResourceNotFoundException("PerfumeNote not found with id: " + id);
        }
        PerfumeNoteDto noteDto = new PerfumeNoteDto(note.getName(), note.getDescription());
        noteDto.setId(note.getId());
        return noteDto;
    }

    @Override
    public List<PerfumeNoteDto> findAllPerfumeNotes() {
        
        List<PerfumeNote> notes = perfumeNoteRepository.findAll();
        List<PerfumeNoteDto> perfumeNoteDtos = new ArrayList<>();
        for (PerfumeNote note : notes) {
            PerfumeNoteDto p =  new PerfumeNoteDto(note.getName(),null);
            p.setId(note.getId());
            perfumeNoteDtos.add(p);
        }
        return perfumeNoteDtos;
    }

    @Override
    public List<PerfumeNoteDto> findLikeNamePerfumeNotes(String name) {
        List<PerfumeNote> notes = perfumeNoteRepository.findLikeName(name);
        List<PerfumeNoteDto> perfumeNoteDtos = new ArrayList<>();
        for (PerfumeNote note : notes) {
            PerfumeNoteDto p =  new PerfumeNoteDto(note.getName(),null);
            p.setId(note.getId());
            perfumeNoteDtos.add(p);
        }
        return perfumeNoteDtos;
    }
}
