package com.essentia.essentiaadministration.service;

import com.essentia.essentiaadministration.dto.BrandDto;

public interface BrandService {
	
	 //create, update, delete
	 BrandDto create(BrandDto b);
	 BrandDto updateBrand(int id, BrandDto b);
	 BrandDto deleteById(int id);
}
