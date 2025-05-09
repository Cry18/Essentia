package com.essentia.essentiauser.service;

import com.essentia.essentiauser.dto.ShelfDto;

public interface ShelfService {
    ShelfDto createShelf(String name);
    ShelfDto deleteShelf(int id);
}
