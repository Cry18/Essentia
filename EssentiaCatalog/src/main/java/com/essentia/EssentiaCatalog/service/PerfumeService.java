package com.essentia.essentiacatalog.service;

import java.util.List;

import com.essentia.essentiacatalog.dto.PerfumeDto;

public interface PerfumeService {
    PerfumeDto details(int id);
    List<PerfumeDto> findAllPerfumes();
    List<PerfumeDto> findPerfumesByFilters(String name, String parfumer, String brand, String note);
}
