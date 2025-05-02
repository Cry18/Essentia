package com.essentia.essentiaAdministration.service;

import com.essentia.essentiaAdministration.dto.PerfumeNoteDto;

public interface PerfumeNoteService {
	 //create, update, delete
	 void create(PerfumeNoteDto prfNote);
	 void updatePerfumeNote(int id, PerfumeNoteDto prfNote);
	 void deleteById(int id);
}
