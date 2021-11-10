package com.demo.cricket.controllers;

import com.demo.cricket.entities.*;
import com.demo.cricket.services.InningsService;
import com.demo.cricket.services.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

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

    @GetMapping ("/startMatch/{matchId}")
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

    @GetMapping ("/startFirstInnings/{matchId}")
    public Match startFirstInnings(@PathVariable("matchId") String matchId) {
        Match match = matchService.getMatchById(matchId);

        // start innings
        Innings innings = match.getFirstInnings();
        startInnings(innings);
        match.setFirstInnings(innings);
        match.setCurrentInningsNumber(innings.getNumber());

        return matchService.updateMatch(match);
    }

    @GetMapping ("/startSecondInnings/{matchId}")
    public Match startSecondInnings(@PathVariable("matchId") String matchId) {
        Match match = matchService.getMatchById(matchId);

        // start innings
        Innings innings = match.getSecondInnings();
        startInnings(innings);
        match.setSecondInnings(innings);
        match.setCurrentInningsNumber(innings.getNumber());

        return matchService.updateMatch(match);
    }

    @GetMapping("/throwBall/{matchId}")
    public Match throwBall(@PathVariable("matchId") String matchId){
        Match match = matchService.getMatchById(matchId);

        if(match.getMatchState() != MatchState.INPROGRESS){
            return match;
        }

        Innings innings = match.getCurrentInningsNumber() == 1 ? match.getFirstInnings() : match.getSecondInnings();
        if(innings.getInningsState() == InningsState.COMPLETED){
            return match;
        }

        setOverAndBallStatus(innings);

        Score currentBallScore = getCurrentBallScore();
        setScore(innings, currentBallScore);

        // check and update innings and match state
        if(match.getNoOfOvers() == innings.getCurrentOverNumber() && innings.getCurrentBallNumber() == 6) {
            innings.setInningsState(InningsState.COMPLETED);
        }

        // update match state if match is over
        if(innings.getInningsState() == InningsState.COMPLETED && match.getCurrentInningsNumber() == 2) {
            // todo: check score for winning team or DRAW
            match.setMatchState(MatchState.COMPLETED);
        }

        if(match.getCurrentInningsNumber() == 1) {
            match.setFirstInnings(innings);
        }else {
            match.setSecondInnings(innings);
        }

        return matchService.updateMatch(match);
    }

    @GetMapping("/getScoreBoard/{matchId}")
    public Match getScoreBoard(@PathVariable("matchId") String matchId){
        return matchService.getMatchById(matchId);
    }

    @GetMapping("/cancelMatch/{matchId}")
    public Match cancelMatch(@PathVariable("matchId") String matchId){
        Match match = matchService.getMatchById(matchId);
        match.setMatchState(MatchState.CANCELLED);

        return matchService.updateMatch(match);
    }

    private void setTossResult(Match match) {
        Random rand = new Random();

        // 0 - first team and 1 - second team
        int team = rand.nextInt(2);
        String teamId = team == 0 ? match.getFirstTeamId() : match.getSecondTeamId();
        match.setTossWinnerTeamId(teamId);

        //  0 - batting and 1 - bowling
        int preference = rand.nextInt(2);
        TeamRole teamRole = preference == 0 ? TeamRole.BATTIG : TeamRole.BOWLING;
        match.setTossWinnerTeamRole(teamRole);
    }

    private void startInnings(Innings innings) {
        List<PlayerStat> battingTeamPLayersStatList = innings.getBattingTeamStat().getPlayersStat();

        PlayerStat firstBatsman = battingTeamPLayersStatList.stream().filter(x ->  x.getPlayerState() == PlayerState.NOTOUT).findAny().get();
        innings.setCurrentFirstBatsmanId(firstBatsman.getPlayerId());

        String nextBatsmanId = getNextBatsman(innings);
        innings.setCurrentSecondBatsmanId(nextBatsmanId);

        String nextBowlerId = getNextBowler(innings);
        innings.setCurrentBowlerId(nextBowlerId);

        innings.setInningsState(InningsState.INPROGRESS);
    }

    private String getNextBowler(Innings innings) {
        List<PlayerStat> bowlingTeamPLayersStatList = innings.getBowlingTeamStat().getPlayersStat();
        PlayerStat bowler = bowlingTeamPLayersStatList.stream().findAny().get();
        return bowler.getPlayerId();
    }

    private String getNextBatsman(Innings innings) {
        List<PlayerStat> battingTeamPLayersStatList = innings.getBattingTeamStat().getPlayersStat();

        PlayerStat nextBatsman = battingTeamPLayersStatList.stream().filter(x -> !x.getPlayerId().equals(innings.getCurrentFirstBatsmanId())
                && x.getPlayerState() == PlayerState.NOTOUT).findAny().get();
        return nextBatsman.getPlayerId();
    }

    private Score getCurrentBallScore() {
        int runs= 0;
        int wickets = 0;

        // randomly generate wicket (0) and runs (1-6)
        int[] weights = {0, 6, 6, 5, 5, 4, 4};

        Random rand = new Random();
        int randomNum = rand.nextInt(Arrays.stream(weights).sum() + 1);

        int i = 0;
        int sum = 0;
        while(i<weights.length) {
            sum += weights[i];
            if(randomNum - sum <= 0) {
                break;
            }
            i++;
        }

        if(i == 0) {
            wickets = 1;
        }
        else{
            runs = i;
        }

        Score score = new Score();
        score.setRuns(runs);
        score.setWickets(wickets);
        return score;
    }

    private void setScore(Innings innings, Score currentBallScore) {
        int currentOverIndex = innings.getCurrentOverNumber() - 1;
        Over currentOver = innings.getOvers().get(currentOverIndex);
        Score currentOverScore = currentOver.getScore();
        currentOverScore.setRuns(currentOverScore.getRuns() + currentBallScore.getRuns());
        currentOverScore.setWickets(currentOverScore.getWickets() + currentBallScore.getWickets());
        currentOver.setScore(currentOverScore);

        int currentBallIndex = innings.getCurrentBallNumber() - 1;
        Ball currentBall = currentOver.getBalls().get(currentBallIndex);
        currentBall.setScore(currentBallScore);

        currentOver.getBalls().set(currentBallIndex, currentBall);

        innings.getOvers().set(currentOverIndex, currentOver);

        Score currentInningsScore = innings.getScore();
        currentInningsScore.setRuns(currentInningsScore.getRuns() + currentBallScore.getRuns());
        currentInningsScore.setWickets(currentInningsScore.getWickets() + currentBallScore.getWickets());
        innings.setScore(currentInningsScore);

        // update batsman score
        if(currentBallScore.getRuns() >0){
            Score batsmanScore = innings.getBattingTeamStat().getPlayerScore(innings.getCurrentFirstBatsmanId());
            batsmanScore.setRuns(batsmanScore.getRuns() + currentBallScore.getRuns());
            innings.getBattingTeamStat().updatePlayerScore(innings.getCurrentFirstBatsmanId(), batsmanScore);
        }

        // no wickets then return
        if (currentBallScore.getWickets() != 1) {
            return;
        }

        // update bowler score
        Score bowlerScore = innings.getBowlingTeamStat().getPlayerScore(innings.getCurrentBowlerId());
        bowlerScore.setRuns(bowlerScore.getRuns() + currentBallScore.getRuns());
        innings.getBowlingTeamStat().updatePlayerScore(innings.getCurrentBowlerId(), bowlerScore);

        // add new wicket
        int wicketNumber = currentInningsScore.getWickets();
        innings.getWickets().add(new Wicket(
                wicketNumber, currentBallIndex, currentOverIndex, innings.getCurrentFirstBatsmanId(), innings.getCurrentBowlerId()));

        // update batsman state
        innings.getBattingTeamStat().updatePlayerState(innings.getCurrentFirstBatsmanId(), PlayerState.OUT);

        // check if no batsman left
        if(wicketNumber == innings.getBattingTeamStat().getPlayersStat().stream().count() - 1)
        {
            innings.setInningsState(InningsState.COMPLETED);
            return;
        }

        // get new batsman
        innings.setCurrentFirstBatsmanId(innings.getCurrentFirstBatsmanId());
        innings.setCurrentSecondBatsmanId(getNextBatsman(innings));
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
            innings.getOvers().add(newOver);
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
