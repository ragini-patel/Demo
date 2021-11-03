package com.demo.cricket.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection  = "team")
@Data
public class Team {
    @Id
    private String id;
    private String teamName;
    private List<String> teamMembers;
}
