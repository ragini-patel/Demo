package com.demo.cricket.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection  = "teamstat")
@Data
@NoArgsConstructor
public class TeamStat {
    @Id
    private String id;
    private String teamId;
    private String matchId;
    private TeamType teamType;
    private Score score;

    public TeamStat(String teamId, String matchId, TeamType teamType) {
        this.teamId = teamId;
        this.matchId = matchId;
        this.teamType = teamType;
    }
}
