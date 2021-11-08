package com.demo.cricket.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection  = "team")
@Data
@NoArgsConstructor
public class Team {
    @Id
    private String id;
    @NonNull
    private String name;
    private List<String> players;
    private OffsetDateTime createdOn;
    private OffsetDateTime updatedOn;

    public Team(String id, String name) {
        this.id = id;
        this.name = name;
        players = new ArrayList<>();
    }
}
