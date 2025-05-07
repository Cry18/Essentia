package com.essentia.EssentiaCatalog.service;

import java.util.List;

import com.essentia.EssentiaCatalog.dto.BrandDto;

public interface BrandService {
    BrandDto details(int id);
    List<BrandDto> findAllBrands();
    List<BrandDto> findLikeNameBrands(String name);
}