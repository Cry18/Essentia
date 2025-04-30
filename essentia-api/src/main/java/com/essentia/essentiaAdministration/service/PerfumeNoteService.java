package com.essentia.essentiaAdministration.service;

import java.util.List;

import com.essentia.essentiaAdministration.dto.PerfumeNoteDto;
import com.essentia.essentiaAdministration.entity.PerfumeNote;

public interface PerfumeNoteService {
	 List<PerfumeNoteDto> findAll();
	 PerfumeNote findById(int id);
	 void create(PerfumeNoteDto prfNote);
	 void updatePerfumeNote(int id, PerfumeNote prfNote);
	 void deleteById(int id);
}
