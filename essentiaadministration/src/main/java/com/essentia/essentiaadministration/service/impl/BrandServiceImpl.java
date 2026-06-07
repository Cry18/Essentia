package com.essentia.essentiaadministration.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.essentia.common.dto.BrandDto;
import com.essentia.essentiaadministration.entity.Brand;
import com.essentia.essentiaadministration.exception.EntityInUseException;
import com.essentia.essentiaadministration.exception.ResourceNotFoundException;
import com.essentia.essentiaadministration.repository.BrandRepository;
import com.essentia.essentiaadministration.repository.PerfumeRepository;
import com.essentia.essentiaadministration.service.BrandService;
@Service
public class BrandServiceImpl implements BrandService {

	private static final Logger logger = LogManager.getLogger(BrandServiceImpl.class);

	@Autowired
	private BrandRepository brandRepository;

	@Autowired
	private PerfumeRepository perfumeRepository;

	@Override
	public BrandDto create(BrandDto b) {
		logger.debug("Creating new brand with name: {}",b.getName());
		Brand brandNew = new Brand(
				b.getName(),
				b.getDescription(),
				b.getNationality());
		brandNew.setImageUrl(b.getImageUrl());
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
		if (b.getImageUrl() != null) {
			brand.setImageUrl(b.getImageUrl());
		}
		brandRepository.save(brand);
		logger.info("Brand with id: {} updated",id);
		b.setId(id);
		return b;		
	}

	@Override
	public BrandDto deleteById(int id) {
		logger.debug("Fetching brand with id: {}", id);
		Brand brand = brandRepository.findById(id);
		if (brand == null) {
			logger.warn("Brand with id: {} not found", id);
			throw new ResourceNotFoundException("Brand not found");
		}
		long perfumeCount = perfumeRepository.countByBrandId(id);
		if (perfumeCount > 0) {
			logger.warn("Cannot delete brand {}: still referenced by {} perfumes", id, perfumeCount);
			throw new EntityInUseException(
				"Non è possibile eliminare questo brand: è associato a " + perfumeCount +
				" profum" + (perfumeCount == 1 ? "o" : "i") + ". Rimuovi prima i profumi collegati."
			);
		}
		brandRepository.delete(brand);
		logger.info("Brand with id: {} deleted", id);
		BrandDto brandDto = new BrandDto(brand.getName(), brand.getDescription(), brand.getNationality());
		brandDto.setId(id);
		return brandDto;
	}

}
