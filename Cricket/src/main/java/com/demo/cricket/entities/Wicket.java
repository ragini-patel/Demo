package com.demo.cricket.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Wicket {
    private int wicketNumber;
    private int ballNumber;
    private int overNumber;
    private String batsmanId;
    private String bowlerId;
}
