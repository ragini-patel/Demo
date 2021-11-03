package com.demo.cricket.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection  = "playerstat")
@Data
public class PlayerStat {
    @Id
    private String id;
    private String playerId;
    private String matchId;
    private String teamId;
    private PlayerType playerType;
    private Score score;
}
