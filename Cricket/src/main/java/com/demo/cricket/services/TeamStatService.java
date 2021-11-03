package com.demo.cricket.services;

public interface TeamStatService {
    String createBattingTeamStat(String battingTeamId, String matchId);

    String createBowlingTeamStat(String bowlingTeamId, String matchId);
}
