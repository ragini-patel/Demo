package com.demo.cricket.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Dictionary;
import java.util.List;

@Document(collection  = "match")
@Data
public class Match {
    @Id
    private String id;
    private Integer noOfOvers;
    private List<Over> overs;
    private String battingTeamId;
    private String bowlingTeamId;
    private String battingTeamStatId;
    private String bowlingTeamStatId;
    private Dictionary<String, String> players;
    private Score score;
    private MatchState matchState;
}
