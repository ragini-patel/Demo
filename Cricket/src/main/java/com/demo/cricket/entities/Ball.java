package com.demo.cricket.entities;

import lombok.Data;

@Data
public class Ball {
    private Integer number;
    private Integer overNumber;
    private String firstBatsmanId;
    private String secondBatsmanId;
    private String bowlerId;
    private Score score;
}