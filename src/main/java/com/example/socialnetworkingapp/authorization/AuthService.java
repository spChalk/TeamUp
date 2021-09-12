package com.example.socialnetworkingapp.authorization;

import com.example.socialnetworkingapp.security.jwt.JwtConfig;
import io.jsonwebtoken.Jwts;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;

    public AuthService(AuthenticationManager authenticationManager, JwtConfig jwtConfig, SecretKey secretKey){
        this.authenticationManager = authenticationManager;
        this.jwtConfig = jwtConfig;
        this.secretKey = secretKey;
    }

    public AuthResponse login(LoginRequest loginRequest){

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        //authentication manager checks whether a user exists and if the password is correct
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = generateToken(authentication);
        return new AuthResponse(loginRequest.getUsername(), token);
    }



    protected String generateToken(Authentication authentication) {
        UserDetails user = (UserDetails) authentication.getPrincipal();
        String access_token = Jwts.builder()
                .setSubject(user.getUsername())
                .claim("authorities", authentication.getAuthorities())
                .setIssuedAt(java.sql.Date.valueOf(LocalDate.now()))
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(jwtConfig.getTokenExpirationAfterDays())))
                .signWith(secretKey)
                .compact();

        return access_token;
    }
}
