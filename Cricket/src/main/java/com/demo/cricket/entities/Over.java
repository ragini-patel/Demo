package com.demo.cricket.entities;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class Over {
    private int number;
    private List<Ball> balls;
    private Score score;

    public Over() {
        balls = new ArrayList<>();
    }
}
