package com.twoyang.prodactivity.server.config.security;

import com.twoyang.prodactivity.server.api.User;
import com.twoyang.prodactivity.server.business.users.UserEntity;
import com.twoyang.prodactivity.server.business.users.UserRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class IdentifierSecretAuthenticationProvider implements AuthenticationProvider {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public IdentifierSecretAuthenticationProvider(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserEntity userEntity = userRepository.findByIdentifier(
            authentication.getPrincipal().toString()
        ).orElseThrow(() -> new BadCredentialsException(""));

        if (passwordEncoder.matches(authentication.getCredentials().toString(), userEntity.getSecret()))
            return new UsernamePasswordAuthenticationToken(userEntity.getId(), null, userEntity.getRoles().stream().map(User.Role::name).map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        throw new BadCredentialsException("");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
