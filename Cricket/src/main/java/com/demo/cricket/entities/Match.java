package com.demo.cricket.entities;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.util.Pair;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection  = "match")
@Data
public class Match {
    @Id
    private String id;
    @NonNull
    private Integer noOfOvers;
    @NonNull
    private String firstTeamId;
    @NonNull
    private String secondTeamId;
    private Innings firstInnings;
    private Innings secondInnings;
    private Integer currentInningNumber;
    private String tossWinnerTeamId;
    private TeamRole tossWinnerTeamRole;
    private MatchState matchState;
    private OffsetDateTime createdOn;
    private OffsetDateTime updatedOn;

    public Match() {
        matchState = MatchState.NOTSTARTED;
    }
}
