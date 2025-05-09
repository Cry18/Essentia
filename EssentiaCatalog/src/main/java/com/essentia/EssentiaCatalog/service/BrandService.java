package com.essentia.essentiacatalog.service;

import java.util.List;

import com.essentia.essentiacatalog.dto.BrandDto;

public interface BrandService {
    BrandDto details(int id);
    List<BrandDto> findAllBrands();
    List<BrandDto> findLikeNameBrands(String name);
}