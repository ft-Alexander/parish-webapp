package org.integ.proj.parishwebapp.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.util.UrlPathHelper;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

//	@Bean
//	public RoleHierarchy roleHierarchy() {
//		RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
//		roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");
//		return roleHierarchy;
//	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((authorize) -> authorize.requestMatchers("/", "/register/**", "/css/**").permitAll()
				.requestMatchers("/login", "/register").permitAll()
				.requestMatchers("/dashboard","/users").hasAnyRole("ADMIN","USER","FINANCE","EXECASS")
				.requestMatchers("/users/view/**","/users/search/**","/users/ascFirstName/**").hasAnyRole("FINANCE","EXECASS","ADMIN")
				.requestMatchers("/users/**").hasAnyRole("EXECASS","ADMIN")
				.requestMatchers("/finance","/finance/**").hasAnyRole("FINANCE","ADMIN"))
				
				
				
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