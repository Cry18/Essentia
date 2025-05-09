package com.essentia.essentiaadministration.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.essentia.essentiaadministration.dto.BrandDto;
import com.essentia.essentiaadministration.entity.Brand;
import com.essentia.essentiaadministration.exception.ResourceNotFoundException;
import com.essentia.essentiaadministration.repository.BrandRepository;
import com.essentia.essentiaadministration.service.BrandService;
@Service
public class BrandServiceImpl implements BrandService {

	@Autowired
	private BrandRepository brandRepository;

	@Override
	public BrandDto create(BrandDto b) {
		Brand brandNew = new Brand(
				b.getName(),
				b.getDescription(),
				b.getNazionality());
		  
				brandRepository.save(brandNew);
				b.setId(brandNew.getId());
				return b;
	}

	@Override
	public BrandDto updateBrand(int id, BrandDto b) {
		Brand brand = brandRepository.findById(id);	
		if (b.getName() != null) {
			brand.setName(b.getName());
		}
		if (b.getDescription() != null) {
			brand.setDescription(b.getDescription());
		}
	
		brandRepository.save(brand);
		b.setId(id);
		return b;		
	}

	@Override
	public BrandDto deleteById(int id) {
		Brand brand = brandRepository.findById(id);
		if (brand != null) {
			brandRepository.delete(brand);
		} else throw new ResourceNotFoundException("Brand not found with id: " + id);
		BrandDto BrandDto = new BrandDto(brand.getName(), brand.getDescription(), brand.getNazionality());
		BrandDto.setId(id);
		return BrandDto;
	}

}
