package com.demo.cricket.services;

import com.demo.cricket.entities.Player;
import com.demo.cricket.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository){
        this.playerRepository = playerRepository;
    }

    @Override
    public Player createPlayer(Player player) {
        player.setCreatedOn(LocalDateTime.now(ZoneOffset.UTC));
        player.setUpdatedOn(LocalDateTime.now(ZoneOffset.UTC));
        return playerRepository.save(player);
    }

    @Override
    public Player getById(String id) {
        return playerRepository.findById(id).orElse(null);
    }

    @Override
    public Player updatePlayer(String id, Player player) {
        return playerRepository.save(player);
    }
}
