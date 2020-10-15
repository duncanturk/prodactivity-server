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
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class IdentifierSecretAuthenticationProvider implements AuthenticationProvider {
    private final UserRepository userRepository;

    public IdentifierSecretAuthenticationProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserEntity userEntity = userRepository.findByIdentifierAndSecret(authentication.getPrincipal().toString(), authentication.getCredentials().toString()).orElseThrow(() -> new BadCredentialsException(""));

        return new UsernamePasswordAuthenticationToken(userEntity.getId(), null, userEntity.getRoles().stream().map(User.Role::name).map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
