package com.demo.cricket.entities;

import org.springframework.data.annotation.Id;

public class Player {
    @Id
    public String id;
    public String name;

    public Player() {
    }
}
