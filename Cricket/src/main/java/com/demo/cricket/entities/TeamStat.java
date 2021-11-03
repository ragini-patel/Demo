package com.demo.cricket.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection  = "teamstat")
@Data
public class TeamStat {
    @Id
    private String id;
    private String teamId;
    private String matchId;
    private TeamType teamType;
    private Score score;
}
