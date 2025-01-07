package com.spring.busbooking.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig{
	
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtAuthenticationFilter jwtFilter;
	
	private PasswordEncoder passwordEncoder;
	
	public SecurityConfig(UserDetailsService userDetailsService, JwtAuthenticationFilter jwtFilter
			,PasswordEncoder passwordEncoder) {
		this.userDetailsService = userDetailsService;
		this.jwtFilter = jwtFilter;
		this.passwordEncoder=passwordEncoder;
	}

	@Bean
	public AccessDeniedHandler accessDeniedHandler()
	{
		return new CustomAccessDeniedHandler();
	}
	
	@Bean
	public AuthenticationEntryPoint entryPoint()
	{
		return new JwtAuthenticationEntryPoint();
	}
	
	//security and permissions
	@Bean
	protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
        .authorizeHttpRequests(authorizeRequests ->
            authorizeRequests
            .requestMatchers("/bus").permitAll()
                .requestMatchers("/register").permitAll()
                .requestMatchers("/login").permitAll()
                .requestMatchers("/token").permitAll()// Specify public endpoints
                .anyRequest().authenticated()
        )
        .cors()
        .and()
        .csrf().disable()
        .exceptionHandling(exceptionHandling ->
            exceptionHandling
                .accessDeniedHandler(accessDeniedHandler())
                .authenticationEntryPoint(entryPoint())
        )
        .sessionManagement(sessionManagement ->
            sessionManagement
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
	
	}
	
}
