package com.demo.cricket.services;

import com.demo.cricket.entities.TeamRole;
import com.demo.cricket.entities.TeamStat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamStatServiceImpl implements TeamStatService {

    private final PlayerStatService playerStatService;

    @Autowired
    public TeamStatServiceImpl(PlayerStatService playerStatService) {
        this.playerStatService = playerStatService;
    }

    @Override
    public TeamStat createBattingTeamStat(String battingTeamId) {
        TeamStat teamStat = new TeamStat(battingTeamId, TeamRole.BATTIG);
        var playersStat = playerStatService.createPlayersStats(battingTeamId);
        teamStat.setPlayersStat(playersStat);
        return teamStat;
    }

    @Override
    public TeamStat createBowlingTeamStat(String bowlingTeamId) {
        TeamStat teamStat = new TeamStat(bowlingTeamId, TeamRole.BOWLING);
        var playersStat = playerStatService.createPlayersStats(bowlingTeamId);
        teamStat.setPlayersStat(playersStat);
        return teamStat;
    }
}
