package com.twoyang.prodactivity.server.api;

import lombok.Data;

import java.util.List;

@Data
public class Category {
    private long id;
    private String color, name;
    private List<Long> tasks;
}
