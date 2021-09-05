package com.example.socialnetworkingapp.security.jwt;

import com.example.socialnetworkingapp.model.account.Account;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;

@Slf4j
public class JwtUsernamePasswordAuthFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;

    public JwtUsernamePasswordAuthFilter(AuthenticationManager authenticationManager, JwtConfig jwtConfig, SecretKey secretKey){
        this.authenticationManager = authenticationManager;
        this.jwtConfig = jwtConfig;
        this.secretKey = secretKey;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        BufferedReader reader = null;
        try {
            reader = request.getReader();
        } catch (Exception e) { throw new IllegalStateException("Could not read buffer!"); }

        JSONTokener tokens = new JSONTokener(reader);
        JSONObject json = new JSONObject(tokens);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(json.get("username"), json.get("password"));
        //authentication manager checks whether a user exists and if the password is correct
        return authenticationManager.authenticate(authenticationToken);
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        UserDetails user = (UserDetails) authResult.getPrincipal();
        String access_token = Jwts.builder()
                .setSubject(user.getUsername())
                .claim("authorities", authResult.getAuthorities())
                .setIssuedAt(java.sql.Date.valueOf(LocalDate.now()))
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(jwtConfig.getTokenExpirationAfterDays())))
                .signWith(secretKey)
                .compact();

        response.addHeader( jwtConfig.getAuthorizationHeader() , jwtConfig.getTokenPrefix() + access_token);
        response.addHeader("Access-Control-Allow-Origin", "http://localhost:4200");
    }
}
