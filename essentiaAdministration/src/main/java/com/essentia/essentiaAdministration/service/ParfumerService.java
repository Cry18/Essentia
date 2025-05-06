package com.essentia.essentiaAdministration.service;

import com.essentia.essentiaAdministration.dto.ParfumerDto;

public interface ParfumerService {

    //create, update, delete
    void create(ParfumerDto p);
    void updateparfumer(int id, ParfumerDto p);
    void deleteById(int id);
}
