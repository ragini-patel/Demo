package com.demo.cricket.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class TeamStat {
    private String teamId;
    private TeamRole teamRole;
    private List<PlayerStat> playersStat;

    public TeamStat(String teamId, TeamRole teamRole) {
        this.teamId = teamId;
        this.teamRole = teamRole;
        this.playersStat = new ArrayList<>();
    }

    public void updatePlayerState(String playerId, PlayerState playerState)
    {
        playersStat.stream().filter(x -> x.getPlayerId().equals(playerId)).findFirst().get().setPlayerState(playerState);
    }
}
