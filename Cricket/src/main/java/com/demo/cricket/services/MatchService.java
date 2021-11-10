package com.demo.cricket.services;

import com.demo.cricket.entities.Match;
import com.demo.cricket.entities.MatchState;

import java.util.List;

public interface MatchService {
    Match getMatchById(String id);

    Match createMatch(Match match);

    Match updateMatch(Match match);

    List<Match> getMatchesByState(MatchState matchState);
}
