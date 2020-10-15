package com.twoyang.prodactivity.server.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import lombok.val;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private static final String TOKEN_PREFIX = "Bearer ";
    private final String SECRET;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public JWTAuthorizationFilter(AuthenticationManager authManager, String secret) {
        super(authManager);
        this.SECRET = secret;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String accessToken = req.getHeader("authorization");

        if (accessToken == null) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = buildAuthentication(req, accessToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken buildAuthentication(HttpServletRequest request, String accessToken) {
        try {
            val claims = Jwts.parser().setSigningKey(SECRET.getBytes()).parseClaimsJws(accessToken.substring(TOKEN_PREFIX.length())).getBody();
            val user = claims.getSubject();

            if (user == null)
                return null;

            val userId = Long.parseLong(user);
            return new UsernamePasswordAuthenticationToken(
                userId,
                null,
                AuthorityUtils.commaSeparatedStringToAuthorityList((String) claims.get("roles")));
        } catch (NumberFormatException | MalformedJwtException e) {
            return null;
        }
    }
}
