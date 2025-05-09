package com.essentia.essentiaadministration.service;

import com.essentia.essentiaadministration.dto.BrandDto;

public interface BrandService {
	
	 //create, update, delete
	 void create(BrandDto b);
	 void updateBrand(int id, BrandDto b);
	 void deleteById(int id);
}
