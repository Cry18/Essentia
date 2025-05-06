package com.essentia.EssentiaCatalog.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.essentia.EssentiaCatalog.dto.BrandDto;
import com.essentia.EssentiaCatalog.entity.Brand;
import com.essentia.EssentiaCatalog.exception.ResourceNotFoundException;
import com.essentia.EssentiaCatalog.repository.BrandRepository;
import com.essentia.EssentiaCatalog.service.BrandService;
@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandRepository brandRepository;

    @Override
    public BrandDto details(int id) {
        Brand brand = brandRepository.findById(id);
        if (brand == null) {
            throw new ResourceNotFoundException("Brand not found with id: " + id);
        }
        BrandDto brandDto = new BrandDto(brand.getName(), brand.getDescription(), brand.getNazionality());
        return brandDto;
    }

    @Override
    public List<String> findAllBrands() {
        
        List<Brand> brands = brandRepository.findAll();
        List<String> brandNames = new ArrayList<>();
        for (Brand brand : brands) {
            brandNames.add(brand.getName());
        }
        return brandNames;
    }

    @Override
    public List<String> findLikeNameBrands(String name) {
        List<String> brandNames = brandRepository.findLikeName(name);
        return brandNames;
    }
}
