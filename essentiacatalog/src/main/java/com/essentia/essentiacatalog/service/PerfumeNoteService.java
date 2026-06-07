package com.essentia.essentiacatalog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.essentia.common.dto.PerfumeNoteDto;

public interface PerfumeNoteService {
    PerfumeNoteDto details(int id);
    Page<PerfumeNoteDto> findAllPerfumeNotes(Pageable pageable);
    Page<PerfumeNoteDto> findLikeNamePerfumeNotes(String name, Pageable pageable);
}
