package com.demo.cricket.entities;

import lombok.Data;
import java.util.List;

@Data
public class Over {
    private Integer number;
    private List<Ball> balls;
    private Score score;
}
