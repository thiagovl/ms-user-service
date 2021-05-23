package com.apimicroservice.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.apimicroservice.user.UserRepository;


@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	UserPrincipalDetailsService userPrincipalDetailsService; 
	@Autowired
	UserRepository userRepository;
	@Autowired
	JwtFilter jwtFilter;
	
	
    /* Configurations for authentication - login */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	auth.userDetailsService(userPrincipalDetailsService).passwordEncoder(new BCryptPasswordEncoder());

    }
    
    /* Encryption type */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }	
    
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /* Configuration for authorization - access */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
    	
        http
                /* Remove csrf and state in session because in jwt we do not need them */
        		.cors().and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)	
                .and()
                
                /* Add jwt filters (1. authentication, 2. authorization) */                
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))                
                .addFilter(new JwtAuthorizationFilter(authenticationManager(),  this.userRepository))
                
                .authorizeRequests()

                /* configure access roles */
//                .antMatchers(HttpMethod.POST, "/**").permitAll() 
                .antMatchers("/**").permitAll();
        
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);   
    }
}
