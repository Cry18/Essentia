package com.essentia.essentiauser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.essentia.essentiauser.entity.Shelf;
import com.essentia.essentiauser.entity.User;
import com.essentia.essentiauser.repository.UserRepository;
import com.essentia.essentiauser.repository.ShelfRepository;
import com.essentia.essentiauser.service.impl.PerfumeServiceImpl;

import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/api/user/shelf/")
public class ShelfController {

    @Autowired
    private PerfumeServiceImpl perfumeService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShelfRepository shelfrepository;
    /*@GetMapping
    public List<ShelfDto> userShelfs(){
        //restituisce tutti gli shelf di un utente
        return null;
    }*/

    @PostMapping("create")
    public void createShelf(@RequestParam(value = "userId") int userId,
                            @RequestParam(value = "shelfName") String shelfName) {
        User user = userRepository.findById(userId);
        Shelf shelf = new Shelf(shelfName, user);
        shelfrepository.save(shelf);
    }

    @DeleteMapping("delete/{id}")
    public void deleteShelf(@PathVariable int id) {
        Shelf shelf = shelfrepository.findById(id);
        shelfrepository.delete(shelf);
    }

    @PostMapping("add")
    public void addToShelf(@RequestParam(value = "userId") int userId, 
                            @RequestParam(value = "perfumeId") int perfumeId) {
        //elimina uno shelf associato ad userId
        return;
    }

    @DeleteMapping("remove")
    public void removeFromShelf(@RequestParam(value = "userId") int userId, 
                            @RequestParam(value = "perfumeId") int perfumeId) {
        //elimina uno shelf associato ad userId
        return;
    }
}
