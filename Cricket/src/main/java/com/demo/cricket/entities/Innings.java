package com.demo.cricket.entities;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class Innings {
    private Integer number;
    private TeamStat battingTeamStat;
    private TeamStat bowlingTeamStat;
    private Integer currentOverNumber;
    private Integer currentBallNumber;
    private String currentFirstBatsmanId;
    private String currentSecondBatsmanId;
    private String currentBowlerId;
    private List<Over> overs;
    private Score score;
    private InningsState inningsState;

    public Innings() {
        overs = new ArrayList<>();
        inningsState = InningsState.NOTSTARTED;
    }
}
