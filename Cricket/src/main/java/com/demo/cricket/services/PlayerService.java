package com.demo.cricket.services;

import com.demo.cricket.entities.Player;

public interface PlayerService {
    Player createPlayer(Player player);

    Player getById(String id);

    Player updatePlayer(String id, Player player);
}
