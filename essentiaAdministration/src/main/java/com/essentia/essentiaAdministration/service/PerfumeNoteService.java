package com.essentia.essentiaadministration.service;

import com.essentia.essentiaadministration.dto.PerfumeNoteDto;

public interface PerfumeNoteService {
	 //create, update, delete
	 void create(PerfumeNoteDto prfNote);
	 void updatePerfumeNote(int id, PerfumeNoteDto prfNote);
	 void deleteById(int id);
}
