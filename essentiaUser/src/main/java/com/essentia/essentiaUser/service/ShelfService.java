package com.essentia.essentiauser.service;

import com.essentia.essentiauser.dto.ShelfDto;

public interface ShelfService {
    ShelfDto createShelf(String name, int userId);
    ShelfDto deleteShelf(int id);
    String addPerfumeToShelf(int shelfId, int perfumeId);
    String removeFromShelf(int shelfId, int perfumeId);
    ShelfDto findShelfById(int shelfId);
}
