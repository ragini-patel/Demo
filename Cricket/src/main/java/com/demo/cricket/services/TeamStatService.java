package com.demo.cricket.services;

import com.demo.cricket.entities.TeamStat;

public interface TeamStatService {
    TeamStat createBattingTeamStat(String battingTeamId);

    TeamStat createBowlingTeamStat(String bowlingTeamId);
}
