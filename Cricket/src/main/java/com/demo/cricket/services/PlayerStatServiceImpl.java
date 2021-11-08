package com.demo.cricket.services;

import com.demo.cricket.entities.*;
import com.demo.cricket.repositories.PlayerHistoryRepository;
import com.demo.cricket.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlayerStatServiceImpl implements PlayerStatService {

    private final TeamRepository teamRepository;

    @Autowired
    public PlayerStatServiceImpl(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public List<PlayerStat> createPlayersStats(String teamId) {
        List<PlayerStat> playersStats = new ArrayList<>();

        Team battingTeam = teamRepository.findById(teamId).get();
        for (String playerId: battingTeam.getPlayers()) {
            PlayerStat playerStat = new PlayerStat(playerId);
            playersStats.add(playerStat);
        }

        return playersStats;
    }
}
