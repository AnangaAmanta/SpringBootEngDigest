package com.snowflake.JournalApp.config;

import com.snowflake.JournalApp.services.CustomeUserDetailsServiceImpl; // Check for typo: should be CustomerUserDetailsServiceImpl
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService; // Added import
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.beans.factory.annotation.Autowired; // Moved and kept for clarity

@Configuration
@EnableWebSecurity
public class SpringSecurity {

    // 💡 Potential Typo: It is common to have Cust**o**meUserDetailsServiceImpl
    // Ensure this matches the actual class name, otherwise it should be CustomerUserDetailsServiceImpl
    @Autowired
    private UserDetailsService userDetailsService;



    /**
     * Security Filter Chain Configuration (Spring Security 6+)
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 1. Disable CSRF for stateless API
        http.csrf(customizer -> customizer.disable());

        // 2. Configure Authorization Rules
        http.authorizeHttpRequests(request -> request
                // Define paths that DO NOT require authentication (permitAll)
                .requestMatchers("/public/**", "/user/register").permitAll() // Added a common registration path
                // Define the catch-all rule: Any other request MUST be authenticated
                .anyRequest().authenticated()
        );

        // 3. Enable HTTP Basic Authentication
        http.httpBasic(Customizer.withDefaults());

        // 💡 Removal Note: In modern Spring Security (6+), explicitly calling
        // http.authenticationManager(...) is usually *not* required when you
        // define a DaoAuthenticationProvider and UserDetailsService beans,
        // as Spring Boot automatically configures the AuthenticationManager.

        return http.build();
    }

    /**
     * DaoAuthenticationProvider Configuration
     * This bean is automatically used by Spring Security to create the AuthenticationManager.
     */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        // Use the injected UserDetailsService/CustomeUserDetailsServiceImpl
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }


    /**
     * Password Encoder Configuration
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Standard and strong algorithm
    }

}