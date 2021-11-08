package com.demo.cricket.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "teamhistory")
@Data
public class TeamHistory {
    @Id
    private String id;
    private String teamId;
    private List<String> players;
}
