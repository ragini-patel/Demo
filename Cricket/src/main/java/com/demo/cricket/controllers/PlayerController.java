package com.demo.cricket.controllers;

import com.demo.cricket.entities.Player;
import com.demo.cricket.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
public class PlayerController {
    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/api/players/{id}")
    public ResponseEntity<Player> getById(@PathVariable("id") String id) {
        Player player = playerService.getById(id);
        return player != null ? new ResponseEntity<>(player, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
