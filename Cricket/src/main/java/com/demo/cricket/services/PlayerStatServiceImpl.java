package com.demo.cricket.services;

import com.demo.cricket.entities.*;
import com.demo.cricket.repositories.PlayerStatRepository;
import com.demo.cricket.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

@Service
public class PlayerStatServiceImpl implements PlayerStatService {

    private final PlayerStatRepository playerStatRepository;
    private final TeamRepository teamRepository;

    @Autowired
    public PlayerStatServiceImpl(PlayerStatRepository playerStatRepository, TeamRepository teamRepository) {
        this.playerStatRepository = playerStatRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public List<PlayerStat> createPlayersStats(Match match) {
        List<PlayerStat> playersStats = new ArrayList<>();

        // create batting team players stats
        String battingTeamId = match.getBattingTeamId();
        Team battingTeam = teamRepository.findById(battingTeamId).get();
        for (String playerId: battingTeam.getPlayers()) {
            PlayerStat batsManStat = createBatsManStat(match, battingTeamId, playerId);
            playersStats.add(batsManStat);
        }

        // create bowling team players stats
        String bowlingTeamId = match.getBowlingTeamId();
        Team bowlingTeam = teamRepository.findById(bowlingTeamId).get();
        for (String playerId: bowlingTeam.getPlayers()) {
            PlayerStat bowlerStat = createBowlerStat(match, battingTeamId, playerId);
            playersStats.add(bowlerStat);
        }

        return playersStats;
    }

    private PlayerStat createBatsManStat(Match match, String battingTeamId, String playerId) {
        PlayerStat playerStat = new PlayerStat(playerId, match.getId(), battingTeamId, PlayerType.BATSMAN);
        playerStat = playerStatRepository.save(playerStat);
        return playerStat;
    }

    private PlayerStat createBowlerStat(Match match, String bowlingTeamId, String playerId) {
        PlayerStat playerStat = new PlayerStat(playerId, match.getId(), bowlingTeamId, PlayerType.BOWLER);
        playerStat = playerStatRepository.save(playerStat);
        return playerStat;
    }
}
