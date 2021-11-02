package com.demo.cricket.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection  = "player")
public class Player {
    @Id
    public String id;
    public String firstName;
    public String lastName;

}
