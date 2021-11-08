package com.demo.cricket.entities;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection  = "player")
@Data
@NoArgsConstructor
public class Player {
    @Id
    private String id;
    private String name;
    private String email;
    private String teamId;

    public Player(String id, String name, String teamId) {
        this.id = id;
        this.name = name;
        this.teamId = teamId;
    }
}
