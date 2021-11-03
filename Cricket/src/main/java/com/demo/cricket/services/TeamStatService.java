package com.demo.cricket.services;

import com.demo.cricket.entities.TeamStat;

public interface TeamStatService {
    TeamStat createBattingTeamStat(String battingTeamId, String matchId);

    TeamStat createBowlingTeamStat(String bowlingTeamId, String matchId);
}
