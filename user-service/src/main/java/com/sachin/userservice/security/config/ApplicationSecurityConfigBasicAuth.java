package com.sachin.userservice.security.config;

import javax.annotation.Resource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.sachin.userservice.security.ApplicationUserRole.*;
import org.springframework.security.core.userdetails.User;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class ApplicationSecurityConfigBasicAuth extends WebSecurityConfigurerAdapter {
	
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfigBasicAuth(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable()  // CSRF disabled, useful for form based
		.authorizeRequests()
//		.antMatchers(HttpMethod.GET, "/index").permitAll() //Allow index without authentication
//		.antMatchers("/v1/user/**", "/v1/role/**").hasAnyRole(ADMIN.name(), CLIENT.name())
		.anyRequest().authenticated()	//all other request needs to be authenticated
		.and()
		.httpBasic();
	}
	

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails adminUser = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("password"))
//                .roles(ADMIN.name()) // ROLE_ADMIN
                .authorities(ADMIN.getGrantedAuthorities())
                .build();

        UserDetails businessUser = User.builder()
                .username("user1")
                .password(passwordEncoder.encode("password"))
//                .roles(CLIENT.name()) // ROLE_CLIENT
                .authorities(CLIENT.getGrantedAuthorities())
                .build();

        return new InMemoryUserDetailsManager(
        		adminUser,
        		businessUser
        );

    }    
}
