package com.essentia.essentia_api.service.api;

import java.util.List;

import com.essentia.essentia_api.entity.Brand;

public interface BrandService {
	 List<Brand> findAll();
	 Brand findById(int id);
	 void save(Brand b);
	 void updateBrand(int id, Brand b);
	 void deleteById(int id);
}
