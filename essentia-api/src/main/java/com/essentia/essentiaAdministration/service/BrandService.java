package com.essentia.essentiaAdministration.service;

import java.util.List;

import com.essentia.essentiaAdministration.dto.BrandDto;

public interface BrandService {
	
	//da eliminare
	 List<BrandDto> findAll();

	 //C(R)UD
	 
	 //create, update, delete
	 void create(BrandDto b);
	 void updateBrand(int id, BrandDto b);
	 void deleteById(int id);
}
