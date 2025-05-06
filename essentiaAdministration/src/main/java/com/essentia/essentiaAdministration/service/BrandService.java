package com.essentia.essentiaAdministration.service;

import com.essentia.essentiaAdministration.dto.BrandDto;

public interface BrandService {
	
	 //create, update, delete
	 void create(BrandDto b);
	 void updateBrand(int id, BrandDto b);
	 void deleteById(int id);
}
