package com.essentia.essentiaAdministration.service;

import java.util.List;

import com.essentia.essentiaAdministration.dto.PerfumeDto;

public interface PerfumeService {

	 //da eliminare
	 List<PerfumeDto> findAll();

	 //C(R)UD

	 //create, update, delete
	 void create(PerfumeDto  perfume);
	 void updatePerfume(int id, PerfumeDto perfume);
	 void deleteById(int id);
}
