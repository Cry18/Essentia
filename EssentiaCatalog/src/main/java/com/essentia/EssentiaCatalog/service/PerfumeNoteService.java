package com.essentia.EssentiaCatalog.service;

import java.util.List;

import com.essentia.EssentiaCatalog.dto.PerfumeNoteDto;

public interface PerfumeNoteService {
    PerfumeNoteDto details(int id);
    List<String> findAllPerfumeNotes();
    List<String> findLikeNamePerfumeNotes(String name);
}
