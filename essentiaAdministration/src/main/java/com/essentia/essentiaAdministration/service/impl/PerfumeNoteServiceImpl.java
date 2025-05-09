package com.essentia.essentiaadministration.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.essentia.essentiaadministration.dto.PerfumeNoteDto;
import com.essentia.essentiaadministration.entity.PerfumeNote;
import com.essentia.essentiaadministration.exception.ResourceNotFoundException;
import com.essentia.essentiaadministration.repository.PerfumeNoteRepository;
import com.essentia.essentiaadministration.service.PerfumeNoteService;

@Service
public class PerfumeNoteServiceImpl implements PerfumeNoteService{
	
	@Autowired
	  private PerfumeNoteRepository perfumeNoteRepository;


	@Override
	public PerfumeNoteDto create(PerfumeNoteDto prfNote) {
		PerfumeNote prfNew = new PerfumeNote(
				prfNote.getName(),
				prfNote.getDescription());
		  
		  perfumeNoteRepository.save(prfNew);
		  prfNote.setId(prfNew.getId());
		  return prfNote;
	}

	@Override
	public PerfumeNoteDto updatePerfumeNote(int id, PerfumeNoteDto note) {
		PerfumeNote prfNote = perfumeNoteRepository.findById(id);	
		if (note.getName() != null) {
			prfNote.setName(note.getName());
		}
		if (note.getDescription() != null) {
			prfNote.setDescription(note.getDescription());
		}
	
		perfumeNoteRepository.save(prfNote);
		note.setId(id);
		return note;
	}

	@Override
	public PerfumeNoteDto deleteById(int id) {
		PerfumeNote prfNote = perfumeNoteRepository.findById(id);
		if (prfNote != null) {
			perfumeNoteRepository.delete(prfNote);
		} else throw new ResourceNotFoundException("PerfumeNote not found with id: " + id);
		PerfumeNoteDto PerfumeNoteDto = new PerfumeNoteDto(prfNote.getName(), prfNote.getDescription());
		PerfumeNoteDto.setId(id);
		return PerfumeNoteDto;
	}


}
