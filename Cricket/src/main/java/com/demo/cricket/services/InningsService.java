package com.demo.cricket.services;

import com.demo.cricket.entities.Innings;
import com.demo.cricket.entities.Match;

public interface InningsService {
    Innings createFirstInnings(Match match);

    Innings createSecondInnings(Match match);

    Innings startFirstInnings(Match match);

    Innings startSecondInnings(Match match);
}
