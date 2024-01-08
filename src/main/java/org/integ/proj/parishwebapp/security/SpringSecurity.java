package org.integ.proj.parishwebapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}


	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((authorize) -> authorize.requestMatchers("/", "/register/**", "/css/**").permitAll()
				.requestMatchers("/login", "/register").permitAll()
				.requestMatchers("/dashboard","/users","/users/view/**","/users/search/**","/users/ascFirstName/**","/finance","/finance/view-financial-record/**","/finance/search/**").hasAnyRole("ADMIN","USER","FINANCE","EXECASS")
				.requestMatchers("/users/**").hasAnyRole("EXECASS","ADMIN")
				.requestMatchers("/finance/**").hasAnyRole("FINANCE","ADMIN"))
				
				
				
				.formLogin(form -> 
						form.loginPage("/login")
						.loginProcessingUrl("/login")	
						.defaultSuccessUrl("/dashboard")
						.permitAll())
				.logout(logout -> 
				logout.logoutRequestMatcher
				(new AntPathRequestMatcher("/logout"))
				.permitAll());
		
		return http.getOrBuild();
	}
	

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
}