package com.twoyang.prodactivity.server.api;

import lombok.Data;

import java.util.List;

@Data
public class User {
    private long id;
    private List<Role> roles;

    public enum Role {
        USER, ADMIN
    }
}
