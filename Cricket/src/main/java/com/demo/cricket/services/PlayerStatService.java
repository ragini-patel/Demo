package com.demo.cricket.services;

import com.demo.cricket.entities.Match;
import com.demo.cricket.entities.PlayerStat;
import java.util.List;

public interface PlayerStatService {

    List<PlayerStat> createPlayersStats(Match match);
}
