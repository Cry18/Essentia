package com.essentia.essentiaadministration.service;

import com.essentia.essentiaadministration.dto.PerfumeDto;

public interface PerfumeService {

	 //create, update, delete
	 PerfumeDto create(PerfumeDto  perfume);
	 PerfumeDto updatePerfume(int id, PerfumeDto perfume);
	 PerfumeDto deleteById(int id);
	 PerfumeDto findMostDesiredPerfume();
	 PerfumeDto findMostAppreciatedPerfume();
}
