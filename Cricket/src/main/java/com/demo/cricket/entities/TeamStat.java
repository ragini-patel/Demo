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
    private Score score;

    public TeamStat(String teamId, TeamRole teamType) {
        this.teamId = teamId;
        this.teamRole = teamRole;
        this.playersStat = new ArrayList<>();
    }
}
