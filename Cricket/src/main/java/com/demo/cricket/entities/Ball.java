package com.demo.cricket.entities;

import lombok.Data;

@Data
public class Ball {
    private Integer overSeq;
    private Integer seq;
    private String firstBatsmanId;
    private String secondBatsmanId;
    private String bowlerId;
}