package com.essentia.essentiacatalog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.essentia.common.dto.BrandDto;

public interface BrandService {
    BrandDto details(int id);
    Page<BrandDto> findAllBrands(Pageable pageable);
    Page<BrandDto> findLikeNameBrands(String name, Pageable pageable);
}
