package com.demo.cricket.services;

import com.demo.cricket.entities.Player;
import com.demo.cricket.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerServiceImpl implements PlayerService {
    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public Player createPlayer(Player player) {
        return playerRepository.save(player);
    }

    @Override
    public Player getById(String id) {
        return playerRepository.findById(id).get();
    }
}
