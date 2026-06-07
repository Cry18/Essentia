package com.essentia.essentiaadministration.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.essentia.common.dto.PerfumeNoteDto;
import com.essentia.essentiaadministration.entity.PerfumeNote;
import com.essentia.essentiaadministration.exception.EntityInUseException;
import com.essentia.essentiaadministration.exception.ResourceNotFoundException;
import com.essentia.essentiaadministration.repository.PerfumeNoteRepository;
import com.essentia.essentiaadministration.repository.PerfumePrfNotesRepository;
import com.essentia.essentiaadministration.service.PerfumeNoteService;

@Service
public class PerfumeNoteServiceImpl implements PerfumeNoteService{

	private static final Logger logger = LogManager.getLogger(PerfumeNoteServiceImpl.class);

	@Autowired
	private PerfumeNoteRepository perfumeNoteRepository;

	@Autowired
	private PerfumePrfNotesRepository perfumePrfNotesRepository;


	@Override
	public PerfumeNoteDto create(PerfumeNoteDto prfNote) {
		logger.debug("Creating new perfume note with name: {}",prfNote.getName());
		PerfumeNote prfNew = new PerfumeNote(
				prfNote.getName(),
				prfNote.getDescription());
		prfNew.setImageUrl(prfNote.getImageUrl());
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
		if (note.getImageUrl() != null) {
			prfNote.setImageUrl(note.getImageUrl());
		}
		perfumeNoteRepository.save(prfNote);
		logger.info("Perfume note with id: {} updated",id);
		note.setId(id);
		return note;
	}

	@Override
	public PerfumeNoteDto deleteById(int id) {
		logger.debug("Fetching perfume note with id: {}", id);
		PerfumeNote prfNote = perfumeNoteRepository.findById(id);
		if (prfNote == null) {
			logger.warn("Perfume note with id: {} not found", id);
			throw new ResourceNotFoundException("PerfumeNote not found with id: " + id);
		}
		long perfumeCount = perfumePrfNotesRepository.countByNoteId(id);
		if (perfumeCount > 0) {
			logger.warn("Cannot delete note {}: still used in {} perfumes", id, perfumeCount);
			throw new EntityInUseException(
				"Non è possibile eliminare questa nota: è utilizzata in " + perfumeCount +
				" profum" + (perfumeCount == 1 ? "o" : "i") + ". Le note in uso non possono essere rimosse."
			);
		}
		perfumeNoteRepository.delete(prfNote);
		logger.info("Perfume note with id: {} deleted", id);
		PerfumeNoteDto perfumeNoteDto = new PerfumeNoteDto(prfNote.getName(), prfNote.getDescription());
		perfumeNoteDto.setId(id);
		return perfumeNoteDto;
	}


}
