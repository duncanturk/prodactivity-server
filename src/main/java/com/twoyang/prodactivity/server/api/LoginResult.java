package com.twoyang.prodactivity.server.api;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class LoginResult {
    String jwt;
}
