package com.demo.cricket.controllers;

import com.demo.cricket.entities.*;
import com.demo.cricket.services.MatchService;
import com.demo.cricket.services.PlayerService;
import com.demo.cricket.services.PlayerStatService;
import com.demo.cricket.services.TeamStatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Dictionary;

@RestController
public class GameController {

    private final MatchService matchService;
    private final TeamStatService teamStatService;
    private final PlayerStatService playerStatService;

    @Autowired
    public GameController(MatchService matchService, TeamStatService teamStatService, PlayerStatService playerStatService) {
        this.matchService = matchService;
        this.teamStatService = teamStatService;
        this.playerStatService = playerStatService;
    }

    @GetMapping("/")
    public String homePage()
    {
        return "Welcome!";
    }

    @GetMapping ("/startMatch/{id}")
    public Match startMatch(@PathVariable("id") String id) {
        Match match = matchService.getMatchById(id);

        // create team stats
        String battingTeamStatId = teamStatService.createBattingTeamStat(match.getBattingTeamId(), match.getId());
        String bowlingTeamStatId = teamStatService.createBowlingTeamStat(match.getBowlingTeamId(), match.getId());
        match.setBattingTeamId(battingTeamStatId);
        match.setBowlingTeamId(bowlingTeamStatId);

        // create players stats
        Dictionary<String, String> playersStats = playerStatService.createPlayersStats(match);
        match.setPlayersStats(playersStats);

        match.setMatchState(MatchState.INPROGRESS);
        return matchService.updateMatch(match);
    }
}
