package com.essentia.essentiaadministration.service;

import com.essentia.essentiaadministration.dto.PerfumeNoteDto;

public interface PerfumeNoteService {
	 //create, update, delete
	 PerfumeNoteDto create(PerfumeNoteDto prfNote);
	 PerfumeNoteDto updatePerfumeNote(int id, PerfumeNoteDto prfNote);
	 PerfumeNoteDto deleteById(int id);
}
