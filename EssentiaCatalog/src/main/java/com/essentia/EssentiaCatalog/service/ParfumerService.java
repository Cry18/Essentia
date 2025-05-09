package com.essentia.essentiacatalog.service;

import java.util.List;

import com.essentia.essentiacatalog.dto.ParfumerDto;

public interface  ParfumerService {
    ParfumerDto details(int id);
    List<ParfumerDto> findAllParfumers();
    List<ParfumerDto> findLikeNameParfumers(String name);
}
