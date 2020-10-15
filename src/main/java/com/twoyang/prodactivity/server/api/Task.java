package com.twoyang.prodactivity.server.api;

import lombok.Data;

import java.util.List;

@Data
public class Task {
    private long id;
    private String name;
    private List<Long> categories;
    private Long interval, goal, remaining;
    private List<Long> bookingSuggestions;
}
