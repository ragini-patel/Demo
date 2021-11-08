package com.demo.cricket.entities;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection  = "team")
@Data
@NoArgsConstructor
public class Team {
    @Id
    private String id;
    private String name;
    private List<String> players;

    public Team(String id, String name) {
        this.id = id;
        this.name = name;
        players = new ArrayList<>();
    }
}
