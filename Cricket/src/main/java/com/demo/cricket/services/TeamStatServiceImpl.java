package com.demo.cricket.services;

import com.demo.cricket.entities.TeamStat;
import com.demo.cricket.entities.TeamType;
import com.demo.cricket.repositories.TeamStatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamStatServiceImpl implements TeamStatService {

    private final TeamStatRepository teamStatRepository;

    @Autowired
    public TeamStatServiceImpl(TeamStatRepository teamStatRepository) {
        this.teamStatRepository = teamStatRepository;
    }

    @Override
    public TeamStat createBattingTeamStat(String battingTeamId, String matchId) {
        TeamStat teamStat = new TeamStat(battingTeamId, matchId, TeamType.BATTIG);
        teamStat =  teamStatRepository.save(teamStat);
        return teamStat;
    }

    @Override
    public TeamStat createBowlingTeamStat(String bowlingTeamId, String matchId) {
        TeamStat teamStat = new TeamStat(bowlingTeamId, matchId, TeamType.BOWLING);
        teamStat =  teamStatRepository.save(teamStat);
        return teamStat;
    }
}
