package com.demo.cricket.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PlayerStat {
    private String playerId;
    private Score score;
    private PlayerState playerState;

    public PlayerStat(String playerId) {
        this.playerId = playerId;
        this.playerState = PlayerState.NOTOUT;
    }
}
