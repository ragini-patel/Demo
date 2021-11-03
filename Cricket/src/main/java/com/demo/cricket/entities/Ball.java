package com.demo.cricket.entities;

import lombok.Data;

@Data
public class Ball {
    private Integer seq;
    private Integer overSeq;
    private String firstBatsmanId;
    private String secondBatsmanId;
    private String bowlerId;
}