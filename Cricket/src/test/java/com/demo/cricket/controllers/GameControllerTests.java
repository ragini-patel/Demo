package com.demo.cricket.controllers;

import com.demo.cricket.entities.*;
import com.demo.cricket.services.InningsService;
import com.demo.cricket.services.MatchService;
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
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(GameController.class)
class GameControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MatchService matchService;
    @MockBean
    private InningsService inningsService;

    private Match match;
    private Team team1;
    private Team team2;
    private String matchId = "testMatch";
    private String team1Id = "team1";
    private String team2Id = "team2";

    @BeforeEach
    void setUp() {
        match = new Match();
        match.setId(matchId);
        match.setNoOfOvers(1);

        team1 = new Team(team1Id, "Team 1");
        team2 = new Team(team2Id, "Team 2");

        Player battingTeamP1 = new Player("team1P1", "team1P1", team1Id);
        Player battingTeamP2 = new Player("team1P2", "team1P2", team1Id);
        List<String> battingTeamPlayers = new ArrayList<>(List.of(battingTeamP1.getId(), battingTeamP2.getId()));
        team1.setPlayers(battingTeamPlayers);

        Player bowlingTeamP1 = new Player("team2P1", "team2P1", team2Id);
        List<String> bowlingTeamPlayers = new ArrayList<>(List.of(bowlingTeamP1.getId()));
        team2.setPlayers(bowlingTeamPlayers);
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

        Innings firstInnings = new Innings();
        firstInnings.setNumber(1);

        Innings secondInnings = new Innings();
        secondInnings.setNumber(2);

        Mockito.when(inningsService.createFirstInnings(match)).thenReturn(firstInnings);
        Mockito.when(inningsService.createSecondInnings(match)).thenReturn(secondInnings);
        Mockito.when(matchService.updateMatch(any(Match.class))).thenAnswer(arg -> arg.getArguments()[0]);

        mockMvc.perform(MockMvcRequestBuilders.get("/startMatch/"+matchId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}