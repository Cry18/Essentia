package com.essentia.essentiaadministration.service;

import com.essentia.essentiaadministration.dto.ParfumerDto;

public interface ParfumerService {

    //create, update, delete
    void create(ParfumerDto p);
    void updateparfumer(int id, ParfumerDto p);
    void deleteById(int id);
}
