package ordersTrackingSystem.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration 
@EnableWebSecurity
@EnableMethodSecurity
public class OrdersSecurityConfiguration {
	@Bean 
	public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {	
			
		http.authorizeHttpRequests().anyRequest().authenticated();
		
		http.httpBasic();
		
		http.csrf().disable();
		
		http.cors();
		
		return http.build();
	}
}