package com.essentia.essentiacatalog.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.essentia.common.dto.BrandDto;
import com.essentia.essentiacatalog.entity.Brand;
import com.essentia.essentiacatalog.exception.ResourceNotFoundException;
import com.essentia.essentiacatalog.repository.BrandRepository;
import com.essentia.essentiacatalog.service.BrandService;

@Service
public class BrandServiceImpl implements BrandService {

    private static final Logger logger = LogManager.getLogger(BrandServiceImpl.class);

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public BrandDto details(int id) {
        logger.debug("Fetching brand with id: {}", id);
        Brand brand = brandRepository.findById(id);
        if (brand == null) {
            logger.warn("Brand not found with id: {}", id);
            throw new ResourceNotFoundException("Brand not found");
        }
        BrandDto brandDto = new BrandDto(brand.getName(), brand.getDescription(), brand.getNationality());
        brandDto.setId(brand.getId());
        brandDto.setImageUrl(brand.getImageUrl());
        logger.info("Brand with id: {} found", id);
        return brandDto;
    }

    @Override
    public Page<BrandDto> findAllBrands(Pageable pageable) {
        logger.debug("Fetching all brands - page: {}, size: {}", pageable.getPageNumber(), pageable.getPageSize());
        return brandRepository.findAll(pageable)
                .map(b -> {
                    BrandDto dto = new BrandDto(b.getName(), null, null);
                    dto.setId(b.getId());
                    dto.setImageUrl(b.getImageUrl());
                    return dto;
                });
    }

    @Override
    public Page<BrandDto> findLikeNameBrands(String name, Pageable pageable) {
        logger.debug("Fetching brands with '{}' in name", name);
        return brandRepository.findLikeName(name, pageable)
                .map(b -> {
                    BrandDto dto = new BrandDto(b.getName(), null, null);
                    dto.setId(b.getId());
                    dto.setImageUrl(b.getImageUrl());
                    return dto;
                });
    }
}
