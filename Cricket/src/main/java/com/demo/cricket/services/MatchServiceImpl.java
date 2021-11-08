package com.demo.cricket.services;

import com.demo.cricket.entities.Match;
import com.demo.cricket.repositories.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class MatchServiceImpl implements MatchService {

    private final MatchRepository matchRepository;

    @Autowired
    public MatchServiceImpl(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    @Override
    public Match getMatchById(String id) {
        return matchRepository.findById(id).get();
    }

    @Override
    public Match createMatch(Match match) {
        match.setCreatedOn(OffsetDateTime.now(ZoneOffset.UTC));
        match.setUpdatedOn(OffsetDateTime.now(ZoneOffset.UTC));
        return matchRepository.save(match);
    }

    @Override
    public Match updateMatch(Match match) {
        match.setUpdatedOn(OffsetDateTime.now(ZoneOffset.UTC));
        return matchRepository.save(match);
    }
}
