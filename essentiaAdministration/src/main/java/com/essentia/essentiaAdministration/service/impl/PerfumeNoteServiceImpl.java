package com.essentia.essentiaAdministration.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.essentia.essentiaAdministration.dto.PerfumeNoteDto;
import com.essentia.essentiaAdministration.entity.PerfumeNote;
import com.essentia.essentiaAdministration.exception.ResourceNotFoundException;
import com.essentia.essentiaAdministration.repository.PerfumeNoteRepository;
import com.essentia.essentiaAdministration.service.PerfumeNoteService;

@Service
public class PerfumeNoteServiceImpl implements PerfumeNoteService{
	
	@Autowired
	  private PerfumeNoteRepository perfumeNoteRepository;


	@Override
	public void create(PerfumeNoteDto prfNote) {
		PerfumeNote prfNew = new PerfumeNote(
				prfNote.getName(),
				prfNote.getDescription());
		  
		  perfumeNoteRepository.save(prfNew);
	}

	@Override
	public void updatePerfumeNote(int id, PerfumeNoteDto note) {
		PerfumeNote prfNote = perfumeNoteRepository.findById(id);	
		// Aggiorna solo i campi forniti
		if (note.getName() != null) {
			prfNote.setName(note.getName());
		}
		if (note.getDescription() != null) {
			prfNote.setDescription(note.getDescription());
		}
	
		perfumeNoteRepository.save(prfNote);
	}

	@Override
	public void deleteById(int id) {
		PerfumeNote prfNote = perfumeNoteRepository.findById(id);
		if (prfNote != null) {
			perfumeNoteRepository.delete(prfNote);
		} else throw new ResourceNotFoundException("PerfumeNote not found with id: " + id);
		
	}


}
