package com.essentia.essentiacatalog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.essentia.essentiacatalog.dto.PerfumeDto;

public interface PerfumeService {
    PerfumeDto details(int id);
    Page<PerfumeDto> findAllPerfumes(Pageable pageable);
    Page<PerfumeDto> findPerfumesByFilters(String name, String parfumer, String brand, String note, Pageable pageable);
}
