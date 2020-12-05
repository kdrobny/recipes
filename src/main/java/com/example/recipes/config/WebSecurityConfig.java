package com.example.recipes.config;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder encoder =
                PasswordEncoderFactories.createDelegatingPasswordEncoder();

        auth
                .inMemoryAuthentication()
                .withUser("user")
                .password(encoder.encode("rpwd"))
                .roles("USER")
                .and()
                .withUser("admin")
                .password(encoder.encode("radmin"))
                .roles("USER", "ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/index.html", "/", "/home", "/login", "/logout").permitAll()
                .antMatchers(HttpMethod.POST, "/api/recipes").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/recipes/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/recipes/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/user", "/api/enums", "/api/recipes", "/api/recipes/*").hasAnyRole("ADMIN", "USER")
                .anyRequest().denyAll()
        ;
    }

}