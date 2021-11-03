package com.demo.cricket.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection  = "player")
@Data
public class Player {
    @Id
    private String id;
    private String name;
    private String email;
    private String teamId;
}
