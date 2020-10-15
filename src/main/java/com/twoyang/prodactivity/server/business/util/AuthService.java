package com.twoyang.prodactivity.server.business.util;

import com.twoyang.prodactivity.server.api.User;
import com.twoyang.prodactivity.server.business.users.UserEntity;
import com.twoyang.prodactivity.server.business.users.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity userEntity() {
        return userRepository.getOne(userID());
    }

    public long userID() {
        return ((long) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    public Set<User.Role> userRoles() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().map(GrantedAuthority::getAuthority).map(Objects::toString).map(User.Role::valueOf).collect(Collectors.toSet());
    }
}
