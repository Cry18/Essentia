package com.essentia.essentiaUser.service;

import java.util.List;


public interface BrandService {
    List<String> findAllBrands();
    List<String> findLikeNameBrands(String name);
}