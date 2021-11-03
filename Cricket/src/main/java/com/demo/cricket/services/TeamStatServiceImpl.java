package com.demo.cricket.services;

import com.demo.cricket.repositories.TeamStatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamStatServiceImpl implements TeamStatService {

    private final TeamStatRepository teamStatRepository;

    @Autowired
    public TeamStatServiceImpl(TeamStatRepository teamStatRepository) {
        this.teamStatRepository = teamStatRepository;
    }
}
