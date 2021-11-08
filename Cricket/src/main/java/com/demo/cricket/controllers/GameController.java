package com.demo.cricket.controllers;

import com.demo.cricket.entities.*;
import com.demo.cricket.services.InningsService;
import com.demo.cricket.services.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    private final MatchService matchService;
    private final InningsService inningsService;

    @Autowired
    public GameController(MatchService matchService, InningsService inningsService) {
        this.matchService = matchService;
        this.inningsService = inningsService;
    }

    @GetMapping("/")
    public String homePage()
    {
        return "Welcome!";
    }

    @GetMapping ("/startMatch/matches/{matchId}")
    public Match startMatch(@PathVariable("matchId") String matchId) {
        Match match = matchService.getMatchById(matchId);

        // toss and select team role
        setTossResult(match);

        // create innings
        Innings firstInnings = inningsService.createFirstInnings(match);
        match.setFirstInnings(firstInnings);

        Innings secondInnings = inningsService.createSecondInnings(match);
        match.setSecondInnings(secondInnings);

        match.setMatchState(MatchState.INPROGRESS);
        return matchService.updateMatch(match);
    }

    @GetMapping ("/startFirstInnings/matches/{matchId}")
    public Match startFirstInnings(@PathVariable("matchId") String matchId) {
        Match match = matchService.getMatchById(matchId);

        // start innings
        Innings innings = inningsService.startFirstInnings(match);
        match.setFirstInnings(innings);
        match.setCurrentInningNumber(innings.getNumber());

        return matchService.updateMatch(match);
    }

    @GetMapping ("/startSecondInnings/matches/{matchId}")
    public Match startSecondInnings(@PathVariable("matchId") String matchId) {
        Match match = matchService.getMatchById(matchId);

        // start innings
        Innings innings = inningsService.startSecondInnings(match);
        match.setSecondInnings(innings);
        match.setCurrentInningNumber(innings.getNumber());

        return matchService.updateMatch(match);
    }

    @GetMapping("/throwBall/matches/{matchId}}")
    public Match throwBall(@PathVariable("matchId") String matchId){
        Match match = matchService.getMatchById(matchId);

        if(match.getMatchState() != MatchState.INPROGRESS){
            return match;
        }

        Innings innings = match.getCurrentInningNumber() == 1 ? match.getFirstInnings() : match.getSecondInnings();

        setOverAndBallStatus(innings);

        Score currentBallScore = getCurrentBallScore();
        setScore(innings, currentBallScore);

        // check and update innings and match state
        if(match.getNoOfOvers() == innings.getCurrentOverNumber() && innings.getCurrentBallNumber() == 6){
            innings.setInningsState(InningsState.COMPLETED);

            if(match.getCurrentInningNumber() == 2)
            {
                // todo: check score for winning team or DRAW
                match.setMatchState(MatchState.COMPLETED);
            }
        }

        return matchService.updateMatch(match);
    }

    @GetMapping("/getScoreBoard/matches/{matchId}")
    public Match getScoreBoard(@PathVariable("matchId") String matchId){
        return matchService.getMatchById(matchId);
    }

    @GetMapping("/cancelMatch/matches/{matchId}")
    public Match cancelMatch(@PathVariable("matchId") String matchId){
        Match match = matchService.getMatchById(matchId);
        match.setMatchState(MatchState.CANCELLED);

        return matchService.updateMatch(match);
    }

    private void setTossResult(Match match) {
        // todo: random generator
        match.setTossWinnerTeamId(match.getFirstTeamId());
        match.setTossWinnerTeamRole(TeamRole.BATTIG);
    }

    private Score getCurrentBallScore() {
        // todo : create random score generator
        Score score = new Score();
        score.setRuns(4);

        return score;
    }

    private void setScore(Innings innings, Score currentBallScore) {
        int overNumber = innings.getCurrentOverNumber() - 1;
        Over currentOver = innings.getOvers().get(overNumber);
        Score currentOverScore = currentOver.getScore();
        currentOverScore.setRuns(currentOverScore.getRuns() + currentBallScore.getRuns());
        currentOverScore.setWickets(currentOverScore.getWickets() + currentBallScore.getWickets());
        currentOver.setScore(currentOverScore);

        int ballNumber = innings.getCurrentBallNumber() - 1;
        Ball currentBall = currentOver.getBalls().get(ballNumber);
        currentBall.setScore(currentBallScore);

        currentOver.getBalls().set(ballNumber, currentBall);

        innings.getOvers().set(overNumber, currentOver);

        Score currentMatchScore = innings.getScore();
        currentOverScore.setRuns(currentOverScore.getRuns() + currentBallScore.getRuns());
        currentOverScore.setWickets(currentOverScore.getWickets() + currentBallScore.getWickets());

        // todo: update teams score

        innings.setScore(currentMatchScore);
    }

    private void setOverAndBallStatus(Innings innings) {
        int currentBallNumber = innings.getCurrentBallNumber();
        currentBallNumber = currentBallNumber % 6 + 1;
        innings.setCurrentBallNumber(currentBallNumber);

        if(currentBallNumber == 1)
        {
            int currentOverNumber = innings.getCurrentOverNumber() + 1;
            innings.setCurrentOverNumber(currentOverNumber);

            Over newOver = new Over();
            newOver.setNumber(currentOverNumber);
        }

        // get current over and set new ball
        Over currentOver = innings.getOvers().get(innings.getCurrentOverNumber() - 1);
        currentOver.getBalls().add(new Ball(
                currentBallNumber,
                innings.getCurrentOverNumber(),
                innings.getCurrentFirstBatsmanId(),
                innings.getCurrentSecondBatsmanId(),
                innings.getCurrentBowlerId()
                ));

        innings.getOvers().set(innings.getCurrentOverNumber() - 1, currentOver);
    }
}
