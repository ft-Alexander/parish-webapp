package org.integ.proj.parishwebapp.controller;

import org.integ.proj.parishwebapp.entity.Staff;
import org.integ.proj.parishwebapp.repository.StaffRepository;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.integ.proj.parishwebapp.entity.Role;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



@Service
public class AuthenticationController implements UserDetailsService {

	private StaffRepository staffRepository;
	private Staff staff;
	public AuthenticationController(StaffRepository staffRepository) {
		this.staffRepository = staffRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Staff staff = staffRepository.findByEmail(email);

	    if (staff != null) {
	        return new org.springframework.security.core.userdetails.User(
	                staff.getEmail(),
	                staff.getPassword(),
	                mapRoleToAuthorities(staff.getRole())
	        );
	    } else {
	        throw new UsernameNotFoundException("invalidEmailOrPassword");
	    }
	}

	private Collection<? extends GrantedAuthority> mapRoleToAuthorities(Role role) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        System.out.print("Mapping role to authorities: " + role.getName());
        // Assuming Role is an enum or has a method to get authority names
        authorities.add(new SimpleGrantedAuthority(role.getName()));

        // Add more authorities as needed based on your application logic

        return authorities;
    }
	
	
}