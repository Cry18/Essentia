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
        brandDto.setId(brand.getId());
        return brandDto;
    }

    @Override
    public List<BrandDto> findAllBrands() {
        
        List<Brand> brands = brandRepository.findAll();
        List<BrandDto> brandDtos = new ArrayList<>();
        for (Brand brand : brands) {
            BrandDto b = new BrandDto(brand.getName(),null,null);
            b.setId(brand.getId());
            brandDtos.add(b);
        }
        return brandDtos;
    }

    @Override
    public List<BrandDto> findLikeNameBrands(String name) {
        List<Brand> brands = brandRepository.findLikeName(name);
        List<BrandDto> brandDtos = new ArrayList<>();
        for (Brand brand : brands) {
            BrandDto b = new BrandDto(brand.getName(),null,null);
            b.setId(brand.getId());
            brandDtos.add(b);
        }
        return brandDtos;

    }
}
