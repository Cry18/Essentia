package com.essentia.essentia_api.service.api;

import java.util.List;

import com.essentia.essentia_api.dto.PerfumeNoteDto;
import com.essentia.essentia_api.entity.PerfumeNote;

public interface PerfumeNoteService {
	 List<PerfumeNoteDto> findAll();
	 PerfumeNote findById(int id);
	 void create(PerfumeNoteDto prfNote);
	 void updatePerfumeNote(int id, PerfumeNote prfNote);
	 void deleteById(int id);
}
