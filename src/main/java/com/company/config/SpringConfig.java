package com.company.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SpringConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //Authentication
        auth.inMemoryAuthentication()
                .withUser("admin").password("{bcrypt}$2a$10$3k59yr14FYhpCb.8/4iqg.vHynyXDT7FLmhKf5WuhhSg7XqKIN2ia").roles("admin")
                .and()
                .withUser("profile").password("{noop}karol7707").roles("profile");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //Authorization
        http.authorizeRequests()
                .antMatchers("/category/**").permitAll()
                .antMatchers("/admin/**").hasAnyRole("admin")
                .antMatchers("/profile/**").hasAnyRole("profile")
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                .csrf().disable()
                .cors().disable()
                .sessionManagement()
                .maximumSessions(1)
                .expiredUrl("/admin/**")
                .maxSessionsPreventsLogin(true)
                .and()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .invalidSessionUrl("/");
        //.formLogin();
    }
}
