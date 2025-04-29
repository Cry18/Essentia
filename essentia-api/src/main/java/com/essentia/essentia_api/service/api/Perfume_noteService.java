package com.essentia.essentia_api.service.api;

import java.util.List;

import com.essentia.essentia_api.dto.Perfume_noteDto;
import com.essentia.essentia_api.entity.Perfume_note;

public interface Perfume_noteService {
	 List<Perfume_note> findAll();
	 Perfume_note findById(int id);
	 void create(Perfume_noteDto prfNote);
	 void updatePerfume_note(int id, Perfume_note prfNote);
	 void deleteById(int id);
}
