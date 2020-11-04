package com.twoyang.prodactivity.server.business.users;

import com.twoyang.prodactivity.server.api.User;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name = "application_users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String identifier;
    private String secret;

    private boolean disabled = false;

    @ElementCollection(targetClass = User.Role.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_role")
    @Column(name = "role")
    private Set<User.Role> roles = Set.of(User.Role.USER);
}
