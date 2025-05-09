package com.essentia.essentiaadministration.service;

import com.essentia.essentiaadministration.dto.PerfumeDto;

public interface PerfumeService {

	 //create, update, delete
	 void create(PerfumeDto  perfume);
	 void updatePerfume(int id, PerfumeDto perfume);
	 void deleteById(int id);
	 PerfumeDto findMostDesiredPerfume();
	 PerfumeDto findMostAppreciatedPerfume();
}
