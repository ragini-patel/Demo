package com.demo.cricket.services;

import com.demo.cricket.entities.Team;
import com.demo.cricket.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public Team createTeam(Team team) {
        team.setCreatedOn(LocalDateTime.now(ZoneOffset.UTC));
        team.setUpdatedOn(LocalDateTime.now(ZoneOffset.UTC));
        return teamRepository.save(team);
    }

    @Override
    public Team getById(String id) {
        return teamRepository.findById(id).get();
    }
}
