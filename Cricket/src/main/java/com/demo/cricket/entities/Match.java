package com.demo.cricket.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection  = "match")
@Data
public class Match {
    @Id
    private String id;
    private Integer noOfOvers;
    private String battingTeamId;
    private String bowlingTeamId;
    private TeamStat battingTeamStat;
    private TeamStat bowlingTeamStat;
    private List<PlayerStat> playersStats;
    private Integer currentOverNumber;
    private Integer currentBallNumber;
    private String currentFirstBatsmanId;
    private String currentSecondBatsmanId;
    private String currentBowlerId;
    private List<Over> overs;
    private Score score;
    private MatchState matchState;

    public Match() {
        playersStats = new ArrayList<>();
        overs = new ArrayList<>();
        matchState = MatchState.NOTSTARTED;
    }
}
