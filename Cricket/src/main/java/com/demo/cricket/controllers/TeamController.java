package com.demo.cricket.controllers;

import com.demo.cricket.entities.Team;
import com.demo.cricket.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TeamController {

    private final TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping("/api/teams/{id}")
    public Team getById(@PathVariable("id") String id) {
        return teamService.getById(id);
    }

    @PostMapping("/api/teams")
    public Team createTeam(@RequestBody Team team){
        return teamService.createTeam(team);
    }
}
