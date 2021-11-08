package com.demo.cricket.controllers;

import com.demo.cricket.entities.*;
import com.demo.cricket.services.MatchService;
import com.demo.cricket.services.PlayerStatService;
import com.demo.cricket.services.TeamStatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class GameController {

    private final MatchService matchService;
    private final TeamStatService teamStatService;
    private final PlayerStatService playerStatService;

    @Autowired
    public GameController(MatchService matchService, TeamStatService teamStatService, PlayerStatService playerStatService) {
        this.matchService = matchService;
        this.teamStatService = teamStatService;
        this.playerStatService = playerStatService;
    }

    @GetMapping("/")
    public String homePage()
    {
        return "Welcome!";
    }

    @GetMapping ("/startMatch/{id}")
    public Match startMatch(@PathVariable("id") String id) {
        Match match = matchService.getMatchById(id);

        // create team stats
        TeamStat battingTeamStat = teamStatService.createBattingTeamStat(match.getBattingTeamId(), match.getId());
        TeamStat bowlingTeamStat = teamStatService.createBowlingTeamStat(match.getBowlingTeamId(), match.getId());
        match.setBattingTeamStat(battingTeamStat);
        match.setBowlingTeamStat(bowlingTeamStat);

        // create players stats
        List<PlayerStat> playerStatList = playerStatService.createPlayersStats(match);
        match.setPlayersStats(playerStatList);

        // set current batsman and bowler details
        PlayerStat firstBatsman = playerStatList.stream().filter(x -> x.getPlayerType() == PlayerType.BATSMAN && x.getPlayerState() == PlayerState.NOTOUT).findAny().get();
        match.setCurrentFirstBatsmanId(firstBatsman.getPlayerId());

        PlayerStat secondBatsman = playerStatList.stream().filter(x ->
                x.getPlayerType() == PlayerType.BATSMAN && x.getPlayerId() != firstBatsman.getPlayerId() && x.getPlayerState() == PlayerState.NOTOUT).findAny().get();
        match.setCurrentSecondBatsmanId(secondBatsman.getPlayerId());

        PlayerStat bowler = playerStatList.stream().filter(x -> x.getPlayerType() == PlayerType.BOWLER).findAny().get();
        match.setCurrentBowlerId(bowler.getPlayerId());

        match.setMatchState(MatchState.INPROGRESS);
        return matchService.updateMatch(match);
    }

    @GetMapping("/throwBall/{id}")
    public Match throwBall(@PathVariable("id") String id){
        Match match = matchService.getMatchById(id);

        if(match.getMatchState() != MatchState.INPROGRESS){
            return match;
        }

        match = setOverAndBallStatus(match);

        Score currentBallScore = getCurrentBallScore();
        match = setScore(match, currentBallScore);

        // check and update match state
        if(match.getNoOfOvers() == match.getCurrentOverNumber() && match.getCurrentBallNumber() == 6){
            match.setMatchState(MatchState.COMPLETED);
        }

        return matchService.updateMatch(match);
    }

    @GetMapping("/getScoreBoard/{id}")
    public Match getScoreBoard(@PathVariable("id") String id){
        return matchService.getMatchById(id);
    }

    @GetMapping("/cancelMatch/{id}")
    public Match cancelBoard(@PathVariable("id") String id){
        Match match = matchService.getMatchById(id);
        match.setMatchState(MatchState.CANCELLED);

        return matchService.updateMatch(match);
    }

    private Score getCurrentBallScore() {
        // todo : create random score generator
        Score score = new Score();
        score.setRuns(4);

        return score;
    }

    private Match setScore(Match match, Score currentBallScore) {
        int overNumber = match.getCurrentOverNumber() - 1;
        Over currentOver = match.getOvers().get(overNumber);
        Score currentOverScore = currentOver.getScore();
        currentOverScore.setRuns(currentOverScore.getRuns() + currentBallScore.getRuns());
        currentOverScore.setWickets(currentOverScore.getWickets() + currentBallScore.getWickets());
        currentOver.setScore(currentOverScore);

        int ballNumber = match.getCurrentBallNumber() - 1;
        Ball currentBall = currentOver.getBalls().get(ballNumber);
        currentBall.setScore(currentBallScore);

        currentOver.getBalls().set(ballNumber, currentBall);

        match.getOvers().set(overNumber, currentOver);

        Score currentMatchScore = match.getScore();
        currentOverScore.setRuns(currentOverScore.getRuns() + currentBallScore.getRuns());
        currentOverScore.setWickets(currentOverScore.getWickets() + currentBallScore.getWickets());

        // todo: update teams score

        match.setScore(currentMatchScore);
        return match;
    }

    private Match setOverAndBallStatus(Match match) {
        int currentBallNumber = match.getCurrentBallNumber();
        currentBallNumber = currentBallNumber % 6 + 1;
        match.setCurrentBallNumber(currentBallNumber);

        if(currentBallNumber == 1)
        {
            int currentOverNumber = match.getCurrentOverNumber() + 1;
            match.setCurrentOverNumber(currentOverNumber);

            Over newOver = new Over();
            newOver.setNumber(currentOverNumber);
        }

        // get current over and set new ball
        Over currentOver = match.getOvers().get(match.getCurrentOverNumber() - 1);
        currentOver.getBalls().add(new Ball(
                currentBallNumber,
                match.getCurrentOverNumber(),
                match.getCurrentFirstBatsmanId(),
                match.getCurrentSecondBatsmanId(),
                match.getCurrentBowlerId()
                ));

        match.getOvers().set(match.getCurrentOverNumber() - 1, currentOver);
        return match;
    }
}
