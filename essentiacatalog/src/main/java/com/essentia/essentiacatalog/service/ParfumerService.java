package com.essentia.essentiacatalog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.essentia.common.dto.ParfumerDto;

public interface ParfumerService {
    ParfumerDto details(int id);
    Page<ParfumerDto> findAllParfumers(Pageable pageable);
    Page<ParfumerDto> findLikeNameParfumers(String name, Pageable pageable);
}
