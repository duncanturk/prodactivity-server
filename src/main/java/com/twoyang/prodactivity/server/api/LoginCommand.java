package com.twoyang.prodactivity.server.api;

import lombok.Data;

@Data
public class LoginCommand {
    private String identifier, secret;
    private LoginMethod method = LoginMethod.USERNAME_PASSWORD;

    enum LoginMethod {
        USERNAME_PASSWORD
    }
}
