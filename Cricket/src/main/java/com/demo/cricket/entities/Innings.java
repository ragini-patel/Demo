package com.demo.cricket.entities;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class Innings {
    private int number;
    private TeamStat battingTeamStat;
    private TeamStat bowlingTeamStat;
    private int currentOverNumber;
    private int currentBallNumber;
    private String currentFirstBatsmanId;
    private String currentSecondBatsmanId;
    private String currentBowlerId;
    private List<Over> overs;
    private List<Wicket> wickets;
    private Score score;
    private InningsState inningsState;

    public Innings() {
        overs = new ArrayList<>();
        wickets = new ArrayList<>();
        inningsState = InningsState.NOTSTARTED;
        score = new Score();
    }
}
