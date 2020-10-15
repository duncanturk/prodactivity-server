package com.twoyang.prodactivity.server.api;

import lombok.Data;

import java.util.List;

@Data
public class TaskCreation {

    private String name;
    private List<Long> categories = List.of();
    private long interval, goal;
}
