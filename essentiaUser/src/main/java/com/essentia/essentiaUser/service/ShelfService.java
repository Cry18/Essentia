package com.essentia.essentiauser.service;

import com.essentia.essentiauser.dto.ShelfDto;

public interface ShelfService {
    ShelfDto createShelf(String name, int userId);
    ShelfDto deleteShelf(int id, int userId);
    String addPerfumeToShelf(int shelfId, int perfumeId, int userId);
    String removeFromShelf(int shelfId, int perfumeId, int userId);
    ShelfDto findShelfById(int shelfId, int userId);
}
