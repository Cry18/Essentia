package com.essentia.essentiaUser.service;

import com.essentia.essentiaUser.dto.ShelfDto;

public interface ShelfService {
    ShelfDto createShelf(String name);
    ShelfDto deleteShelf(int id);
}
