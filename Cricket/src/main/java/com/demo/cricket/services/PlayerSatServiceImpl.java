package com.demo.cricket.services;

import com.demo.cricket.repositories.PlayerStatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerSatServiceImpl implements PlayerSatService {

    private final PlayerStatRepository playerStatRepository;

    @Autowired
    public PlayerSatServiceImpl(PlayerStatRepository playerStatRepository) {
        this.playerStatRepository = playerStatRepository;
    }
}
