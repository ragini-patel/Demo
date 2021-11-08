package com.demo.cricket.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection  = "playerstat")
@Data
@NoArgsConstructor
public class PlayerStat {
    @Id
    private String id;
    private String playerId;
    private String matchId;
    private String teamId;
    private PlayerType playerType;
    private Score score;
    private PlayerState playerState;

    public PlayerStat(String playerId, String matchId, String teamId, PlayerType playerType) {
        this.playerId = playerId;
        this.matchId = matchId;
        this.teamId = teamId;
        this.playerType = playerType;
        this.playerState = PlayerState.NOTOUT;
    }
}
