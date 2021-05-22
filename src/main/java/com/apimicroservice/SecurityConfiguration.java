package com.apimicroservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }	

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	    	
        http
                /* Remove csrf and state in session because in jwt we do not need them */
        		.cors().and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)	
                .and()
                .authorizeRequests()              
                .antMatchers("/**").permitAll();
    }
}