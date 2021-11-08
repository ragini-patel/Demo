package com.demo.cricket.controllers;

import com.demo.cricket.entities.*;
import com.demo.cricket.services.MatchService;
import com.demo.cricket.services.PlayerStatService;
import com.demo.cricket.services.TeamStatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;

@WebMvcTest(GameController.class)
class GameControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MatchService matchService;
    @MockBean
    private TeamStatService teamStatService;
    @MockBean
    private PlayerStatService playerStatService;

    private Match match;
    private Team battingTeam;
    private Team bowlingTeam;
    private String matchId = "testMatch";
    private String battingTeamId = "battingTeam";
    private String bowlingTeamId = "bowlingTeam";

    @BeforeEach
    void setUp() {
        match = new Match();
        match.setId(matchId);
        match.setNoOfOvers(1);
        match.setBattingTeamId(battingTeamId);
        match.setBowlingTeamId(bowlingTeamId);

        battingTeam = new Team(battingTeamId, "Batting Team");
        bowlingTeam = new Team(bowlingTeamId, "Bowling Team");

        Player battingTeamP1 = new Player("battingTeamP1", "battingTeamP1", battingTeamId);
        Player battingTeamP2 = new Player("battingTeamP2", "battingTeamP2", battingTeamId);
        List<String> battingTeamPlayers = new ArrayList<>(List.of(battingTeamP1.getId(), battingTeamP2.getId()));
        battingTeam.setPlayers(battingTeamPlayers);

        Player bowlingTeamP1 = new Player("bowlingTeamP1", "bowlingTeamP1", bowlingTeamId);
        List<String> bowlingTeamPlayers = new ArrayList<>(List.of(bowlingTeamP1.getId()));
        bowlingTeam.setPlayers(bowlingTeamPlayers);
    }

    @Test
    void homePage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Welcome!"));
    }

    @Test
    void startMatch() throws Exception {
        Mockito.when(matchService.getMatchById(matchId)).thenReturn(match);

        TeamStat battingTeamStat = new TeamStat(match.getBattingTeamId(), match.getId(), TeamType.BATTIG);
        TeamStat bowlingTeamStat = new TeamStat(match.getBowlingTeamId(), match.getId(), TeamType.BOWLING);
        Mockito.when(teamStatService.createBattingTeamStat(match.getBattingTeamId(), match.getId())).thenReturn(battingTeamStat);
        Mockito.when(teamStatService.createBowlingTeamStat(match.getBowlingTeamId(), match.getId())).thenReturn(bowlingTeamStat);

        List<PlayerStat> playerStatList = new ArrayList<>();
        playerStatList.add(new PlayerStat("battingTeamP1", matchId, battingTeamId, PlayerType.BATSMAN));
        playerStatList.add(new PlayerStat("battingTeamP2", matchId, battingTeamId, PlayerType.BATSMAN));
        playerStatList.add(new PlayerStat("bowlingTeamP1", matchId, bowlingTeamId, PlayerType.BOWLER));
        Mockito.when(playerStatService.createPlayersStats(match)).thenReturn(playerStatList);

        Mockito.when(matchService.updateMatch(any(Match.class))).thenAnswer(arg -> arg.getArguments()[0]);

        mockMvc.perform(MockMvcRequestBuilders.get("/startMatch/"+matchId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}