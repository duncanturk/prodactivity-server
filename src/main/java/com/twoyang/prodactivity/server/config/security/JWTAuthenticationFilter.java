package com.twoyang.prodactivity.server.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twoyang.prodactivity.server.api.LoginCommand;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.val;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.stream.Collectors;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String SECRET;
    private final long TOKEN_TTL;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, String secret, long ttl) {
        this.authenticationManager = authenticationManager;
        this.SECRET = secret;
        this.TOKEN_TTL = ttl;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        try {
            val loginCommand = objectMapper.readValue(req.getInputStream(), LoginCommand.class);

            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginCommand.getIdentifier(), loginCommand.getSecret()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException {
        val roles = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

        val claims = new HashMap<String, Object>();
        claims.put("roles", roles);

        val token = Jwts.builder().setClaims(claims).setSubject(String.valueOf(auth.getPrincipal()))
            .setExpiration(new Date(System.currentTimeMillis() + TOKEN_TTL)).signWith(SignatureAlgorithm.HS256, SECRET.getBytes())
            .compact();

        res.setStatus(HttpServletResponse.SC_OK);
        res.getWriter().write(token);
        res.getWriter().flush();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        SecurityContextHolder.clearContext();
        response.setStatus(401);
    }
}
