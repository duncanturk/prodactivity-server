package com.twoyang.prodactivity.server.api;

import lombok.Data;
import org.springframework.lang.NonNull;

import java.util.List;

@Data
public class TaskCreation {
    @NonNull
    private String color, name;
    private List<Long> categories = List.of();
    private long interval, goal;
}
