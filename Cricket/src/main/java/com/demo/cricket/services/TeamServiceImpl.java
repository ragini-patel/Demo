package com.demo.cricket.services;

import com.demo.cricket.repositories.TeamStatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamServiceImpl implements TeamService {

    private final TeamStatRepository teamStatRepository;

    @Autowired
    public TeamServiceImpl(TeamStatRepository teamStatRepository) {
        this.teamStatRepository = teamStatRepository;
    }
}
