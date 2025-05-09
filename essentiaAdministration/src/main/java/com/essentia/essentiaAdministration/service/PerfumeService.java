package com.essentia.essentiaAdministration.service;

import com.essentia.essentiaAdministration.dto.PerfumeDto;

public interface PerfumeService {

	 //create, update, delete
	 void create(PerfumeDto  perfume);
	 void updatePerfume(int id, PerfumeDto perfume);
	 void deleteById(int id);
	 PerfumeDto findMostDesiredPerfume();
	 PerfumeDto findMostAppreciatedPerfume();
}
