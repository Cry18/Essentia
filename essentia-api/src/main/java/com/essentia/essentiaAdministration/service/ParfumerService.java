package com.essentia.essentiaAdministration.service;

import java.util.List;

import com.essentia.essentiaAdministration.dto.ParfumerDto;

public interface ParfumerService {
	
	//da eliminare
    List<ParfumerDto> findAll();

    //C(R)UD
    
    //create, update, delete
    void create(ParfumerDto p);
    void updateparfumer(int id, ParfumerDto p);
    void deleteById(int id);
}
