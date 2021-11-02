package com.demo.cricket.controllers;

import com.demo.cricket.entities.Player;
import com.demo.cricket.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PlayerController {
    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/api/players/{id}")
    public Player getById(@PathVariable("id") String id) {
        return playerService.getById(id);
    }

    @PostMapping("/api/players")
    public Player createPlayer(@RequestBody Player player) {
        return playerService.createPlayer(player);
    }

    @PutMapping("/api/players/id")
    public Player updatePlayer(@PathVariable("id") String id, @RequestBody Player player) {
        return playerService.updatePlayer(id, player);
    }
}
