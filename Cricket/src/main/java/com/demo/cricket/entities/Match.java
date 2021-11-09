package com.demo.cricket.entities;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection  = "match")
@Data
public class Match {
    @Id
    private String id;
    @NonNull
    private int noOfOvers;
    @NonNull
    private String firstTeamId;
    @NonNull
    private String secondTeamId;
    private Innings firstInnings;
    private Innings secondInnings;
    private int currentInningsNumber;
    private String tossWinnerTeamId;
    private TeamRole tossWinnerTeamRole;
    private MatchState matchState;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

    public Match() {
        matchState = MatchState.NOTSTARTED;
    }
}
