package com.demo.cricket.services;

import com.demo.cricket.entities.Match;
import java.util.Dictionary;

public interface PlayerStatService {

    Dictionary<String, String> createPlayersStats(Match match);
}
