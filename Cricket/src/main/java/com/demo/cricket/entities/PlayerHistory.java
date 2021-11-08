package com.demo.cricket.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "playerhistory")
@Data
public class PlayerHistory {
    @Id
    private String id;
    private String playerId;
    private String teamId;
}
