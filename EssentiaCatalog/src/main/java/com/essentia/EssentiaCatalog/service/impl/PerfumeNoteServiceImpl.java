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
        return noteDto;
    }

    @Override
    public List<String> findAllPerfumeNotes() {
        
        List<PerfumeNote> notes = perfumeNoteRepository.findAll();
        List<String> noteNames = new ArrayList<>();
        for (PerfumeNote note : notes) {
            noteNames.add(note.getName());
        }
        return noteNames;
    }

    @Override
    public List<String> findLikeNamePerfumeNotes(String name) {
        List<String> noteNames = perfumeNoteRepository.findLikeName(name);
        return noteNames;
    }
}
