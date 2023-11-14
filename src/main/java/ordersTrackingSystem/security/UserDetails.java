package ordersTrackingSystem.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class UserDetails {
	@Bean
	public UserDetailsService userDetailsSecurity(PasswordEncoder passwordEncoder) {
		org.springframework.security.core.userdetails.UserDetails user = 
				User.withUsername("user")
				.password(passwordEncoder.encode("user"))
				.roles("USER")
				.build();

		org.springframework.security.core.userdetails.UserDetails admin =
				User.withUsername("admin")
				.password(passwordEncoder.encode("admin"))
				.roles("ADMIN", "USER").build();

		return new InMemoryUserDetailsManager(user, admin);
	}

	@Bean
	public PasswordEncoder encode() {
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		return encoder;
//		return new BCryptPasswordEncoder();
	}
}