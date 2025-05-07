package com.essentia.essentiaUser.service;

import java.util.List;

public interface PerfumeNoteService {
    List<String> findAllPerfumeNotes();
    List<String> findLikeNamePerfumeNotes(String name);
}
