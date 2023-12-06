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

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@Service
public class AuthenticationController implements UserDetailsService {

	private StaffRepository staffRepository;

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
//		return Collections.singleton(new SimpleGrantedAuthority(role.getName()));
		Set<GrantedAuthority> authorities = new HashSet<>();

	    // Add authorities based on the role
	    if (role != null) {
	        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
	    }

	    // You can add more authorities as needed
	    authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
	    authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

	    return authorities;
	}
}