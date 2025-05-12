package com.essentia.essentiauser.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.essentia.essentiauser.dto.ShelfDto;
import com.essentia.essentiauser.entity.Perfume;
import com.essentia.essentiauser.entity.Shelf;
import com.essentia.essentiauser.entity.User;
import com.essentia.essentiauser.exception.ResourceNotFoundException;
import com.essentia.essentiauser.repository.PerfumeRepository;
import com.essentia.essentiauser.repository.ShelfRepository;
import com.essentia.essentiauser.repository.UserRepository;
import com.essentia.essentiauser.service.ShelfService;

@Service
public class ShelfServiceImpl implements ShelfService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShelfRepository shelfrepository;

    @Autowired
    private PerfumeRepository perfumeRepository;

    @Override
    public ShelfDto createShelf(String name, int userId) {
        User user = userRepository.findById(userId);
        Shelf shelf = new Shelf(name, user);
        shelfrepository.save(shelf);
        ShelfDto shelfDto = new ShelfDto(name);
        shelfDto.setId(shelf.getId());
        return shelfDto;
    }

    @Override
    public ShelfDto deleteShelf(int id) {
        Shelf shelf = shelfrepository.findById(id);
        if (shelf != null) {
            shelfrepository.delete(shelf);
            ShelfDto shelfDto = new ShelfDto(shelf.getName());
            shelfDto.setId(id);
            return shelfDto;
        }  else throw new ResourceNotFoundException("Shelf not found with id: " + id);
        }

    @Override
    public String addPerfumeToShelf(int shelfId, int perfumeId) {
        Shelf shelf = shelfrepository.findById(shelfId);
        if (shelf != null) {
            Perfume perfume = perfumeRepository.findById(perfumeId);
            if (perfume == null) {
                throw new ResourceNotFoundException("Perfume not found with id: " + perfumeId);}
            shelf.addPerfume(perfume);
            shelfrepository.save(shelf);
            return perfume.getName() + " added to shelf " + shelf.getName();
        } else throw new ResourceNotFoundException("Shelf not found with id: " + shelfId);
    }

    @Override
    public String removeFromShelf(int shelfId, int perfumeId) {
        Shelf shelf = shelfrepository.findById(shelfId);
        if (shelf != null) {
            Perfume perfume = perfumeRepository.findById(perfumeId);
            if (perfume == null) {
                throw new ResourceNotFoundException("Perfume not found with id: " + perfumeId);}
            if (shelf.removePerfume(perfume)){
                shelfrepository.save(shelf);
                return perfume.getName() + " removed from shelf " + shelf.getName();
            } throw new ResourceNotFoundException("There is no perfume with id: " + perfumeId + ", in shelf: " + shelf.getName());
        } else throw new ResourceNotFoundException("Shelf not found with id: " + shelfId);
    }

    @Override
    public ShelfDto findShelfById(int shelfId) {
        Shelf shelf = shelfrepository.findByIdWithPerfumes(shelfId);
        if (shelf != null) {
            ShelfDto shelfDto = new ShelfDto(shelf.getName());
            shelfDto.setId(shelf.getId());
            shelfDto.setPerfumes(shelf.getPerfumes().stream().map(Perfume::getName).toList());
            return shelfDto;
        } else throw new ResourceNotFoundException("Shelf not found with id: " + shelfId);
    }
}

