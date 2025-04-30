package com.essentia.essentia_api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.essentia.essentia_api.dto.PerfumeNoteDto;
import com.essentia.essentia_api.entity.PerfumeNote;
import com.essentia.essentia_api.repository.PerfumeNoteRepository;
import com.essentia.essentia_api.service.api.PerfumeNoteService;

@Service
public class PerfumeNoteServiceImpl implements PerfumeNoteService{
	
	@Autowired
	  private PerfumeNoteRepository perfumeNoteRepository;

	  @Override
	  public List<PerfumeNoteDto> findAll() {
		  List<PerfumeNote> notes = (List<PerfumeNote>) perfumeNoteRepository.findAll();
		  List<PerfumeNoteDto> notesDto = notes.stream()
			  .map(note -> new PerfumeNoteDto(note.getName(), note.getDescription()))
			  .toList();
		  return notesDto;
	  }

	@Override
	public PerfumeNote findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void create(PerfumeNoteDto prfNote) {
		PerfumeNote prfNew = new PerfumeNote(
				prfNote.getName(),
				prfNote.getDescription());
		  
		  perfumeNoteRepository.save(prfNew);
	}

	@Override
	public void updatePerfumeNote(int id, PerfumeNote b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(int id) {
		// TODO Auto-generated method stub
		
	}


}
