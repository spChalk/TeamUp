package com.example.socialnetworkingapp.security.config;

import com.example.socialnetworkingapp.model.account.AccountService;
import com.example.socialnetworkingapp.security.jwt.JwtConfig;
import com.example.socialnetworkingapp.security.jwt.JwtTokenVerifier;
import com.example.socialnetworkingapp.security.jwt.JwtUsernamePasswordAuthFilter;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final AccountService accountService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final SecretKey secretKey;
    private final JwtConfig jwtConfig;

    @Autowired
    public WebSecurityConfig(AccountService accountService, BCryptPasswordEncoder bCryptPasswordEncoder, SecretKey secretKey, JwtConfig jwtConfig) {
        this.accountService = accountService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.secretKey = secretKey;
        this.jwtConfig = jwtConfig;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/register/**").permitAll()
                                .antMatchers("/login/**").permitAll()
                                .antMatchers("/accounts/**").permitAll()
                                .antMatchers("/export/**").permitAll()
                                .antMatchers("/upload/**").permitAll()
                                .antMatchers("/files/**").permitAll()
                                .antMatchers("/bio/**").permitAll()
                                .antMatchers("/posts/**").permitAll()
                                .antMatchers("/jobs/**").permitAll()
                                .antMatchers("/crequest/**").permitAll()
                                .antMatchers("/download/**").permitAll()
                                .antMatchers("/jobapp/**").permitAll()
                                .antMatchers("/job_views/**").permitAll()
                                .antMatchers("/tags/**").permitAll()
                                .antMatchers("/xp/**").permitAll()
                                .antMatchers("/education/**").permitAll()
                .anyRequest().authenticated();

//        http.addFilter(new JwtUsernamePasswordAuthFilter(authenticationManagerBean(),jwtConfig, secretKey));
        http.addFilterBefore(new JwtTokenVerifier(secretKey, jwtConfig), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(accountService);
        return provider;
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }
}
