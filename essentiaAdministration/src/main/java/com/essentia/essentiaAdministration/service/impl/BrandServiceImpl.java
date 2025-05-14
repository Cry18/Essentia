package com.essentia.essentiaadministration.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.essentia.essentiaadministration.dto.BrandDto;
import com.essentia.essentiaadministration.entity.Brand;
import com.essentia.essentiaadministration.exception.ResourceNotFoundException;
import com.essentia.essentiaadministration.repository.BrandRepository;
import com.essentia.essentiaadministration.service.BrandService;
@Service
public class BrandServiceImpl implements BrandService {

	private static final Logger logger = LogManager.getLogger(BrandServiceImpl.class);

	@Autowired
	private BrandRepository brandRepository;

	@Override
	public BrandDto create(BrandDto b) {
		logger.debug("Creating new brand with name: {}",b.getName());
		Brand brandNew = new Brand(
				b.getName(),
				b.getDescription(),
				b.getNazionality());

				brandRepository.save(brandNew);
				logger.info("Brand with name: {} created", b.getName());
				b.setId(brandNew.getId());
				return b;
	}

	@Override
	public BrandDto updateBrand(int id, BrandDto b) {
		logger.debug("Fetching brand with id: {}",id);
		Brand brand = brandRepository.findById(id);	
		if(brand == null){
			logger.warn("Brand not found with id: {}",id);
			throw new ResourceNotFoundException("Brand not found");
		}
		if (b.getName() != null) {
			brand.setName(b.getName());
		}
		if (b.getDescription() != null) {
			brand.setDescription(b.getDescription());
		}
		brandRepository.save(brand);
		logger.info("Brand with id: {} updated",id);
		b.setId(id);
		return b;		
	}

	@Override
	public BrandDto deleteById(int id) {
		logger.debug("Fetching brand with id: {}",id);
		Brand brand = brandRepository.findById(id);
		if (brand != null) {
			brandRepository.delete(brand);
			logger.info("Brand with id: {} deleted",id);
		} else {
			logger.warn("Brand with id: {} not found",id);
			throw new ResourceNotFoundException("Brand not found");}
		BrandDto BrandDto = new BrandDto(brand.getName(), brand.getDescription(), brand.getNazionality());
		BrandDto.setId(id);
		return BrandDto;
	}

}
