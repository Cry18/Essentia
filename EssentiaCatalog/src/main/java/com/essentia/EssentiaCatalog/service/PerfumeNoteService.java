package com.essentia.EssentiaCatalog.service;

import java.util.List;

import com.essentia.EssentiaCatalog.dto.PerfumeNoteDto;

public interface PerfumeNoteService {
    PerfumeNoteDto details(int id);
    List<PerfumeNoteDto> findAllPerfumeNotes();
    List<PerfumeNoteDto> findLikeNamePerfumeNotes(String name);
}
