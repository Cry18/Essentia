package com.essentia.essentiaAdministration.service;

import java.util.List;

import com.essentia.essentiaAdministration.dto.PerfumeNoteDto;

public interface PerfumeNoteService {

	 //da eliminare
	 List<PerfumeNoteDto> findAll();

	 //C(R)UD

	 //create, update, delete
	 void create(PerfumeNoteDto prfNote);
	 void updatePerfumeNote(int id, PerfumeNoteDto prfNote);
	 void deleteById(int id);
}
