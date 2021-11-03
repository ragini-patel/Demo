package com.demo.cricket.services;

import com.demo.cricket.entities.Team;

public interface TeamService {
    Team createTeam(Team team);

    Team getById(String id);
}
