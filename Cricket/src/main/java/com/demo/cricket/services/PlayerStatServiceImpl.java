package com.demo.cricket.services;

import com.demo.cricket.entities.Match;
import com.demo.cricket.entities.PlayerStat;
import com.demo.cricket.entities.PlayerType;
import com.demo.cricket.entities.Team;
import com.demo.cricket.repositories.PlayerStatRepository;
import com.demo.cricket.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Dictionary;
import java.util.Hashtable;

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
    public Dictionary<String, String> createPlayersStats(Match match) {
        Dictionary<String, String> playersStats = new Hashtable<>();

        // create batting team players stats
        String battingTeamId = match.getBattingTeamId();
        Team battingTeam = teamRepository.findById(battingTeamId).get();
        for (String playerId: battingTeam.getPlayers()) {
            String batsManStatId = createBatsManStat(match, battingTeamId, playerId);
            playersStats.put(playerId, batsManStatId);
        }

        // create bowling team players stats
        String bowlingTeamId = match.getBowlingTeamId();
        Team bowlingTeam = teamRepository.findById(bowlingTeamId).get();
        for (String playerId: bowlingTeam.getPlayers()) {
            String bowlerStatId = createBowlerStat(match, battingTeamId, playerId);
            playersStats.put(playerId, bowlerStatId);
        }

        return playersStats;
    }

    private String createBatsManStat(Match match, String battingTeamId, String playerId) {
        PlayerStat playerStat = new PlayerStat(playerId, match.getId(), battingTeamId, PlayerType.BATSMAN);
        playerStat = playerStatRepository.save(playerStat);
        return playerStat.getId();
    }

    private String createBowlerStat(Match match, String bowlingTeamId, String playerId) {
        PlayerStat playerStat = new PlayerStat(playerId, match.getId(), bowlingTeamId, PlayerType.BOWLER);
        playerStat = playerStatRepository.save(playerStat);
        return playerStat.getId();
    }
}
