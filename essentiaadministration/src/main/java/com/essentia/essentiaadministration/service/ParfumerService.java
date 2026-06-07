package com.essentia.essentiaadministration.service;

import com.essentia.common.dto.ParfumerDto;

public interface ParfumerService {

    //create, update, delete
    ParfumerDto create(ParfumerDto p);
    ParfumerDto updateparfumer(int id, ParfumerDto p);
    ParfumerDto deleteById(int id);
}
