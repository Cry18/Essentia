package com.essentia.essentiauser.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.essentia.essentiauser.dto.ShelfDto;
import com.essentia.essentiauser.service.impl.ShelfServiceImpl;

@RestController
@RequestMapping("/api/user/shelf/")
public class ShelfController {

    @Autowired
    private ShelfServiceImpl shelfService;


    @GetMapping("detail/{id}")
    public ShelfDto shelfDetail(@PathVariable int id) {
        return shelfService.findShelfById(id);
    }

    @PostMapping("create/")
    public ShelfDto createShelf(@RequestParam(value = "userId") int userId,
                            @RequestParam(value = "shelfName") String shelfName) {
        return shelfService.createShelf(shelfName, userId);
    }

    @DeleteMapping("delete/{id}")
    public ShelfDto deleteShelf(@PathVariable int id) {
        return shelfService.deleteShelf(id);
    }

    @PutMapping("add/")
    public String addToShelf(@RequestParam(value = "shelfId") int shelfId, 
                            @RequestParam(value = "perfumeId") int perfumeId) {
       return shelfService.addPerfumeToShelf(shelfId, perfumeId);
    }

    @PutMapping("remove/")
    public String removeFromShelf(@RequestParam(value = "shelfId") int shelfId, 
                            @RequestParam(value = "perfumeId") int perfumeId) {
       return shelfService.removeFromShelf(shelfId, perfumeId);
    }
}
