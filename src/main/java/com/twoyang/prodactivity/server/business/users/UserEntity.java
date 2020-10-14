package com.twoyang.prodactivity.server.business.users;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "application_users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LoginMethod loginMethod = LoginMethod.USERNAME_PASSWORD;

    enum LoginMethod {
        USERNAME_PASSWORD
    }
}
