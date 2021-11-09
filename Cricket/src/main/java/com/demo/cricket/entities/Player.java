package com.demo.cricket.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection  = "player")
@Data
@NoArgsConstructor
public class Player {
    @Id
    private String id;
    @NonNull
    private String name;
    private String email;
    private String teamId;
    private PlayerType playerType;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

    public Player(String id, String name, String teamId) {
        this.id = id;
        this.name = name;
        this.teamId = teamId;
    }
}
