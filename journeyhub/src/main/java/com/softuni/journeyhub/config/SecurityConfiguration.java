package com.softuni.journeyhub.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/scripts/**", "/styles/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
                http
                .authorizeRequests()
                .antMatchers("/", "/login", "/register", "/api/**").permitAll()
                        .antMatchers("/locations", "/location/add").hasAnyRole("MODERATOR", "ADMIN")
                        .antMatchers("/place/add", "/places/edit/**", "/places/delete/**").hasAnyRole("MODERATOR", "ADMIN")
                        .antMatchers("/users/**").hasRole("ADMIN")
                        .antMatchers("/forum/topic/delete/**").hasAnyRole("MODERATOR", "ADMIN")
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .formLogin().loginPage("/login")
                        .defaultSuccessUrl("/",true)
                        .failureUrl("/login?error")
                .usernameParameter("username")
                .passwordParameter("password")
                .and()
                        .logout().permitAll().logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                .and()
                        .exceptionHandling().accessDeniedPage("/unauthorized");


    }
}
