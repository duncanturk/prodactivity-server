package com.twoyang.prodactivity.server.api;

import lombok.Data;

@Data
public class UserCreation {
    private String identifier, secret;
}
