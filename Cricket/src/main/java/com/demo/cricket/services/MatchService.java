package com.demo.cricket.services;

import com.demo.cricket.entities.Match;

public interface MatchService {
    Match getMatchById(String id);

    Match createMatch(Match match);

    Match updateMatch(Match match);
}
