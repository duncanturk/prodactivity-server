package com.twoyang.prodactivity.server.business.authentication;

import com.twoyang.prodactivity.server.business.users.UserEntity;
import lombok.Data;
import org.springframework.lang.NonNull;

import javax.persistence.*;

@Data
@Table(name = "username_password",
        uniqueConstraints = @UniqueConstraint(columnNames = "for_user"))
public class UsernamePasswordEntity {

    @Column(name = "for_user")
    private UserEntity forUser;

    @NonNull
    private String username, password;
}
