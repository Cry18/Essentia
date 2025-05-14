package com.essentia.essentiaadministration.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.essentia.essentiaadministration.dto.PerfumeNoteDto;
import com.essentia.essentiaadministration.entity.PerfumeNote;
import com.essentia.essentiaadministration.exception.ResourceNotFoundException;
import com.essentia.essentiaadministration.repository.PerfumeNoteRepository;
import com.essentia.essentiaadministration.service.PerfumeNoteService;

@Service
public class PerfumeNoteServiceImpl implements PerfumeNoteService{

	private static final Logger logger = LogManager.getLogger(PerfumeNoteServiceImpl.class);
	
	@Autowired
	  private PerfumeNoteRepository perfumeNoteRepository;


	@Override
	public PerfumeNoteDto create(PerfumeNoteDto prfNote) {
		logger.debug("Creating new perfume note with name: {}",prfNote.getName());
		PerfumeNote prfNew = new PerfumeNote(
				prfNote.getName(),
				prfNote.getDescription());
		  perfumeNoteRepository.save(prfNew);
		  logger.info("Perfume note with name: {} created", prfNote.getName());
		  prfNote.setId(prfNew.getId());
		  return prfNote;
	}

	@Override
	public PerfumeNoteDto updatePerfumeNote(int id, PerfumeNoteDto note) {
		logger.debug("Fetching perfume note with id: {}",id);
		PerfumeNote prfNote = perfumeNoteRepository.findById(id);
		if(prfNote == null){
			logger.warn("Perfume note not found with id: {}",id);
			throw new ResourceNotFoundException("Perfume note not found");
		}	
		if (note.getName() != null) {
			prfNote.setName(note.getName());
		}
		if (note.getDescription() != null) {
			prfNote.setDescription(note.getDescription());
		}
		perfumeNoteRepository.save(prfNote);
		logger.info("Perfume note with id: {} updated",id);
		note.setId(id);
		return note;
	}

	@Override
	public PerfumeNoteDto deleteById(int id) {
		logger.debug("Fetching perfume note with id: {}",id);
		PerfumeNote prfNote = perfumeNoteRepository.findById(id);
		if (prfNote != null) {
			perfumeNoteRepository.delete(prfNote);
			logger.info("Perfume note with id: {} deleted",id);
		} else {
			logger.warn("Perfume note with id: {} not found",id);
			throw new ResourceNotFoundException("PerfumeNote not found with id: " + id);}
		PerfumeNoteDto PerfumeNoteDto = new PerfumeNoteDto(prfNote.getName(), prfNote.getDescription());
		PerfumeNoteDto.setId(id);
		return PerfumeNoteDto;
	}


}
