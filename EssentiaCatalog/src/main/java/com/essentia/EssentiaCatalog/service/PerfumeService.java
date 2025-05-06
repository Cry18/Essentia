package com.essentia.EssentiaCatalog.service;

import java.util.List;

import com.essentia.EssentiaCatalog.dto.PerfumeDto;

public interface PerfumeService {
    PerfumeDto details(int id);
    List<String> findAllPerfumes();
    List<String> findPerfumesByFilters(String name, String parfumer, String brand, String note);
}
