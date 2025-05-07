package com.essentia.EssentiaCatalog.service;

import java.util.List;

import com.essentia.EssentiaCatalog.dto.ParfumerDto;

public interface  ParfumerService {
    ParfumerDto details(int id);
    List<ParfumerDto> findAllParfumers();
    List<ParfumerDto> findLikeNameParfumers(String name);
}
