package com.example.socialnetworkingapp.security.config;

import com.example.socialnetworkingapp.model.account.AccountService;
import com.example.socialnetworkingapp.security.jwt.JwtTokenVerifier;
import com.example.socialnetworkingapp.security.jwt.JwtUsernamePasswordAuthFilter;
import lombok.AllArgsConstructor;
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

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final AccountService accountService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/register/**").permitAll()
//                .antMatchers("/admin").hasRole("ADMIN")
//                .antMatchers("/user").hasRole("USER")
//                .anyRequest().authenticated()
//                .and()
//                .formLogin();
//    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("springuser").password(new PasswordEncoder().bCryptPasswordEncoder().encode("spring123")).roles("USER")
//                .and()
//                .withUser("springadmin").password(new PasswordEncoder().bCryptPasswordEncoder().encode("admin123")).roles("ADMIN", "USER");
//    }


//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/registration/**").permitAll()//white list this route
//                .antMatchers("/admin/**").hasRole(AppUserRole.USER.name())
//                .anyRequest()
//                .authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/login").permitAll()
//                .defaultSuccessUrl("/welcome", true) //redirect after login
//                .and()
//                .logout()
//                .logoutUrl("/logout")
//                .clearAuthentication(true)
//                .invalidateHttpSession(true)
//                .deleteCookies("JSESSIONID")
//                .logoutSuccessUrl("/login");
//    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/register/**").permitAll();
        http.authorizeRequests().antMatchers("/login/**").permitAll();
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(new JwtUsernamePasswordAuthFilter(authenticationManagerBean()));
        http.addFilterAfter(new JwtTokenVerifier(), JwtUsernamePasswordAuthFilter.class);
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
