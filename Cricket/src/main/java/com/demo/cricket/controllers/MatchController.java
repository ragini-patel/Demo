package com.demo.cricket.controllers;

import com.demo.cricket.entities.Match;
import com.demo.cricket.entities.MatchState;
import com.demo.cricket.services.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MatchController {
    private final MatchService matchService;

    @Autowired
    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping("/api/matches/{id}")
    public Match getMatchById(@PathVariable("id") String id){
        return matchService.getMatchById(id);
    }

    @PostMapping("/api/matches")
    public Match createMatch(@RequestBody Match match){
        return matchService.createMatch(match);
    }

    @GetMapping("/api/matches")
    public List<Match> getMatchById(@RequestParam("matchState") MatchState matchState){
        return matchService.getMatchesByState(matchState);
    }
}
