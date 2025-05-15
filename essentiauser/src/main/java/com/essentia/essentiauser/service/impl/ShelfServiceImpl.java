package com.essentia.essentiauser.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.essentia.essentiauser.dto.ShelfDto;
import com.essentia.essentiauser.entity.Perfume;
import com.essentia.essentiauser.entity.Shelf;
import com.essentia.essentiauser.entity.User;
import com.essentia.essentiauser.exception.ForbiddenActionException;
import com.essentia.essentiauser.exception.NoNameShelfExcpetion;
import com.essentia.essentiauser.exception.ResourceNotFoundException;
import com.essentia.essentiauser.repository.PerfumeRepository;
import com.essentia.essentiauser.repository.ShelfRepository;
import com.essentia.essentiauser.repository.UserRepository;
import com.essentia.essentiauser.service.ShelfService;

@Service
public class ShelfServiceImpl implements ShelfService {

    private static final Logger logger = LogManager.getLogger(ShelfServiceImpl.class);

    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShelfRepository shelfrepository;

    @Autowired
    private PerfumeRepository perfumeRepository;

    @Override
    public ShelfDto createShelf(String name, int userId) {
        if (name.isBlank()) {
            logger.warn("Trying to create a shelf with blank name");
            throw new NoNameShelfExcpetion("Impossible to create shelf with blank name");
        }
        logger.debug("Fetching user with id: {}", userId);
        User user = userRepository.findById(userId);
        if (user == null) {
            logger.warn("User not found with id: {}", userId);
            throw new ResourceNotFoundException("User not found");
        }
        Shelf shelf = new Shelf(name, user);
        shelfrepository.save(shelf);
        logger.info("Shelf with name: {} created", name);
        ShelfDto shelfDto = new ShelfDto(name);
        shelfDto.setId(shelf.getId());
        return shelfDto;
    }

    @Override
    public ShelfDto deleteShelf(int id, int userId) {
        logger.debug("Fetching shelf with id: {}", id);
        Shelf shelf = shelfrepository.findById(id);
        if (shelf != null) {
            if (shelf.getUser().getId() != userId) {
                logger.warn("Shelf with id: {} does not belong to user with id: {}", id, userId);
                throw new ForbiddenActionException("This shelf does not belong to the user with id: " + userId);
            } else {
            shelfrepository.delete(shelf);
            logger.info("Shelf with id: {} deleted", id);
            ShelfDto shelfDto = new ShelfDto(shelf.getName());
            shelfDto.setId(id);
            return shelfDto;}
        }  else {
            logger.warn("Shelf with id: {} not found", id);
            throw new ResourceNotFoundException("Shelf not found");}
        }

    @Override
    public String addPerfumeToShelf(int shelfId, int perfumeId, int userId) {
        logger.debug("Fetching shelf with id: {}", shelfId);
        Shelf shelf = shelfrepository.findById(shelfId);
        if (shelf != null) {
            if (shelf.getUser().getId() != userId) {
                logger.warn("Shelf with id: {} does not belong to user with id: {}", shelfId, userId);
                throw new ForbiddenActionException("This shelf does not belong to the user with id: " + userId);
            } else {
            logger.debug("Fetching perfume with id: {}", perfumeId);
            Perfume perfume = perfumeRepository.findById(perfumeId);
            if (perfume == null) {
                logger.warn("Perfume with id: {} not found", perfumeId);
                throw new ResourceNotFoundException("Perfume not found");}
            shelf.addPerfume(perfume);
            shelfrepository.save(shelf);
            logger.info("Perfume with id: {} added to shelf with id: {}", perfumeId, shelfId);
            return perfume.getName() + " added to shelf " + shelf.getName();}
        } else {
            logger.warn("Shelf with id: {} not found", shelfId);
            throw new ResourceNotFoundException("Shelf not found");}
    }

    @Override
    public String removeFromShelf(int shelfId, int perfumeId, int userId) {
        logger.debug("Fetching shelf with id: {}", shelfId);
        Shelf shelf = shelfrepository.findById(shelfId);
        if (shelf != null) {
            if (shelf.getUser().getId() != userId) {
                logger.warn("Shelf with id: {} does not belong to user with id: {}", shelfId, userId);
                throw new ForbiddenActionException("This shelf does not belong to the user with id: " + userId);
            } else {
            logger.debug("Fetching perfume with id: {}", perfumeId);
            Perfume perfume = perfumeRepository.findById(perfumeId);
            if (perfume == null) {
                logger.warn("Perfume with id: {} not found", perfumeId);
                throw new ResourceNotFoundException("Perfume not found");}
            if (shelf.removePerfume(perfume)){
                shelfrepository.save(shelf);
                logger.info("Perfume with id: {} removed from shelf with id: {}", perfumeId, shelfId);
                return perfume.getName() + " removed from shelf " + shelf.getName();}
            } logger.warn("Perfume with id: {} not found in shelf with id: {}", perfumeId, shelfId);
            throw new ResourceNotFoundException("There is no perfume with id: " + perfumeId + ", in shelf: " + shelf.getName());
        } else {
            logger.warn("Shelf with id: {} not found", shelfId);
            throw new ResourceNotFoundException("Shelf not found");}
    }

    @Override
    public ShelfDto findShelfById(int shelfId, int userId) {
        logger.debug("Fetching shelf with id: {}", shelfId);
        Shelf shelf = shelfrepository.findByIdWithPerfumes(shelfId);
        if (shelf != null) {
            if (shelf.getUser().getId() != userId) {
                logger.warn("Shelf with id: {} does not belong to user with id: {}", shelfId, userId);
                throw new ForbiddenActionException("This shelf does not belong to the user with id: " + userId);
            } else {
            ShelfDto shelfDto = new ShelfDto(shelf.getName());
            shelfDto.setId(shelf.getId());
            shelfDto.setPerfumes(shelf.getPerfumes().stream().map(Perfume::getName).toList());
            logger.info("Shelf with id: {} found", shelfId);
            return shelfDto;}
        } else {
            logger.warn("Shelf with id: {} not found", shelfId);
            throw new ResourceNotFoundException("Shelf not found");}
    }
}

