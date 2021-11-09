package com.demo.cricket.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Ball {
    private int number;
    private int overNumber;
    private String firstBatsmanId;
    private String secondBatsmanId;
    private String bowlerId;
    private Score score;

    public Ball(int number, int overNumber, String firstBatsmanId, String secondBatsmanId, String bowlerId) {
        this.number = number;
        this.overNumber = overNumber;
        this.firstBatsmanId = firstBatsmanId;
        this.secondBatsmanId = secondBatsmanId;
        this.bowlerId = bowlerId;
    }
}