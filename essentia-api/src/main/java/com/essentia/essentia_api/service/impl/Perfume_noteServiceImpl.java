package com.essentia.essentia_api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.essentia.essentia_api.dto.Perfume_noteDto;
import com.essentia.essentia_api.entity.Perfume_note;
import com.essentia.essentia_api.repository.Perfume_noteRepository;
import com.essentia.essentia_api.service.api.Perfume_noteService;

@Service
public class Perfume_noteServiceImpl implements Perfume_noteService{
	
	@Autowired
	  private Perfume_noteRepository perfume_noteRepository;

	@Override
	public List<Perfume_note> findAll() {
		List <Perfume_note> notes = (List<Perfume_note>) perfume_noteRepository.findAll();
		List<Perfume_noteDto> notesDto;
		return ;
	}

	@Override
	public Perfume_note findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void create(Perfume_noteDto prfNote) {
		Perfume_note prfNew = new Perfume_note(
				prfNote.getName(),
				prfNote.getDescription());
		  
		  perfume_noteRepository.save(prfNew);
	}

	@Override
	public void updatePerfume_note(int id, Perfume_note b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(int id) {
		// TODO Auto-generated method stub
		
	}


}
