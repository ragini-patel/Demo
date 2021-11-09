package com.demo.cricket.services;

import com.demo.cricket.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InningsServiceImpl implements InningsService {

    private final TeamStatService teamStatService;

    @Autowired
    public InningsServiceImpl(TeamStatService teamStatService) {
        this.teamStatService = teamStatService;
    }

    @Override
    public Innings createFirstInnings(Match match) {
        String battingTeamId;
        String bowlingTeamId;

        if(match.getTossWinnerTeamRole() == TeamRole.BATTIG){
            battingTeamId = match.getTossWinnerTeamId();
            bowlingTeamId = match.getFirstTeamId() == battingTeamId ? match.getSecondTeamId() : match.getFirstTeamId();
        }else {
            bowlingTeamId = match.getTossWinnerTeamId();
            battingTeamId = match.getFirstTeamId() == bowlingTeamId ? match.getSecondTeamId() : match.getFirstTeamId();
        }

        Innings innings = createInnings(1, battingTeamId, bowlingTeamId);

        return innings;
    }

    @Override
    public Innings createSecondInnings(Match match) {
        String battingTeamId;
        String bowlingTeamId;

        if(match.getTossWinnerTeamRole() == TeamRole.BOWLING){
            battingTeamId = match.getTossWinnerTeamId();
            bowlingTeamId = match.getFirstTeamId() == battingTeamId ? match.getSecondTeamId() : match.getFirstTeamId();
        }else {
            bowlingTeamId = match.getTossWinnerTeamId();
            battingTeamId = match.getFirstTeamId() == bowlingTeamId ? match.getSecondTeamId() : match.getFirstTeamId();
        }

        Innings innings = createInnings(2, battingTeamId, bowlingTeamId);

        return innings;
    }

    public Innings createInnings(int inningsNumber, String battingTeamId, String bowlingTeamId) {
        Innings innings = new Innings();
        innings.setNumber(inningsNumber);

        // create team stats
        TeamStat battingTeamStat = teamStatService.createBattingTeamStat(battingTeamId);
        TeamStat bowlingTeamStat = teamStatService.createBowlingTeamStat(bowlingTeamId);
        innings.setBattingTeamStat(battingTeamStat);
        innings.setBowlingTeamStat(bowlingTeamStat);

        innings.setInningsState(InningsState.NOTSTARTED);

        return innings;
    }
}
