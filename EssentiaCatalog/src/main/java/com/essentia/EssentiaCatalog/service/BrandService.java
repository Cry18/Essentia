package com.essentia.EssentiaCatalog.service;

import java.util.List;

import com.essentia.EssentiaCatalog.dto.BrandDto;

public interface BrandService {
    BrandDto details(int id);
    List<String> findAllBrands();
    List<String> findLikeNameBrands(String name);
}