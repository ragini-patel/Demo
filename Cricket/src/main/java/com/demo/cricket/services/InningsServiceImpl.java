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

    @Override
    public Innings startFirstInnings(Match match) {
        Innings innings = match.getFirstInnings();

        startInnings(innings);

        return innings;
    }

    @Override
    public Innings startSecondInnings(Match match) {
        Innings innings = match.getSecondInnings();

        startInnings(innings);

        return innings;
    }

    public Innings createInnings(Integer inningsNumber, String battingTeamId, String bowlingTeamId) {
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

    private void startInnings(Innings innings) {
        List<PlayerStat> battingTeamPLayersStatList = innings.getBattingTeamStat().getPlayersStat();

        PlayerStat firstBatsman = battingTeamPLayersStatList.stream().filter(x ->  x.getPlayerState() == PlayerState.NOTOUT).findAny().get();
        innings.setCurrentFirstBatsmanId(firstBatsman.getPlayerId());

        PlayerStat secondBatsman = battingTeamPLayersStatList.stream().filter(x -> x.getPlayerId() != firstBatsman.getPlayerId()
                && x.getPlayerState() == PlayerState.NOTOUT).findAny().get();
        innings.setCurrentSecondBatsmanId(secondBatsman.getPlayerId());

        List<PlayerStat> bowlingTeamPLayersStatList = innings.getBowlingTeamStat().getPlayersStat();
        PlayerStat bowler = bowlingTeamPLayersStatList.stream().findAny().get();
        innings.setCurrentBowlerId(bowler.getPlayerId());

        innings.setInningsState(InningsState.INPROGRESS);
    }
}
