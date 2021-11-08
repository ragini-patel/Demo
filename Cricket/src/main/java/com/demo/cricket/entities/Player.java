package com.demo.cricket.entities;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.OffsetDateTime;
import java.util.Date;

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
    private OffsetDateTime createdOn;
    private OffsetDateTime updatedOn;

    public Player(String id, String name, String teamId) {
        this.id = id;
        this.name = name;
        this.teamId = teamId;
    }
}
