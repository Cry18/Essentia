package com.essentia.essentiacatalog.service;

import java.util.List;

import com.essentia.essentiacatalog.dto.PerfumeNoteDto;

public interface PerfumeNoteService {
    PerfumeNoteDto details(int id);
    List<PerfumeNoteDto> findAllPerfumeNotes();
    List<PerfumeNoteDto> findLikeNamePerfumeNotes(String name);
}
