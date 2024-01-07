package org.integ.proj.parishwebapp.service.impl;

import org.integ.proj.parishwebapp.dto.StaffDto;
import org.integ.proj.parishwebapp.entity.Role;
import org.integ.proj.parishwebapp.entity.Staff;
import org.integ.proj.parishwebapp.repository.RoleRepository;
import org.integ.proj.parishwebapp.repository.StaffRepository;
import org.integ.proj.parishwebapp.service.StaffService;
import org.springframework.data.util.Streamable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StaffServiceImpl implements StaffService{
	  private StaffRepository staffRepository;
	    private RoleRepository roleRepository;
	    private PasswordEncoder passwordEncoder;

	    public StaffServiceImpl(StaffRepository staffRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
			super();
			this.staffRepository = staffRepository;
			this.roleRepository = roleRepository;
			this.passwordEncoder = passwordEncoder;
		}

	    @Override
	    public void saveUser(StaffDto staffDto) {
	    	Staff staff = new Staff();
	        staff.setFname(staffDto.getFname());
	        staff.setMname(staffDto.getMname());
	        staff.setLname(staffDto.getLname());
	        staff.setName(staffDto.getFname() + " " + staffDto.getMname() + " " + staffDto.getLname());
	        staff.setEmail(staffDto.getEmail());
	        // encrypt the password using spring security
	        staff.setPassword(passwordEncoder.encode(staffDto.getPassword()));
	        staff.setEmployementDate(staffDto.getEmployementDate());

	        // Find or create the "ROLE_USER" role
	        Role role = roleRepository.findByName("ROLE_USER");
	        if (role == null) {
	            role = createRole("ROLE_USER");
	        }

	        staff.setRole(role);
	        staffRepository.save(staff);
	    }

	    @Override
	    public Staff findUserByEmail(String email) {
	        return staffRepository.findByEmail(email);
	    }

	    @Override
	    public Staff findUserById(Long id) {
	    	return staffRepository.findById(id).get();
	    }
	    
//	    @Override
//	    public Staff editUser(Staff staff) {
//	    	return staffRepository.save(staff);
//	    }
	    
	    @Override
	    public void deleteUserById(Long id) {
	    	staffRepository.deleteById(id);
	    }
	    
	    @Override
	    public List<StaffDto> findAllUsers() {
	        List<Staff> staff = staffRepository.findAll();
	        return staff.stream()
	                .map((staffs) -> mapToUserDto(staffs))
	                .collect(Collectors.toList());
	    }

	    private StaffDto mapToUserDto(Staff user){
	        StaffDto staffDto = new StaffDto();
	        //String[] str = user.getName().split(" ");
	        //staffDto.setFname(str[0]);
	        //staffDto.setLname(str[1]);
	        staffDto.setId(user.getId());
	        staffDto.setFname(user.getFname());
	        staffDto.setMname(user.getMname());
	        staffDto.setLname(user.getLname());
	        staffDto.setEmail(user.getEmail());
	        staffDto.setEmployementDate(user.getEmployementDate());
	        staffDto.setPhoneNumber(user.getPhoneNumber());
	        return staffDto;
	    }

	    private Role createRole(String roleName) {
	        Role role = new Role();
	        role.setName(roleName);
	        return roleRepository.save(role);
	    }

		@Override
		public List<Staff> search(String keyword) {
			if(keyword != null) {
				return staffRepository.search(keyword);
			}
			return staffRepository.findAll();
		}

		@Override
		public void editUser(Staff userData, Long id, Long Editor) {
			Staff existingUser = findUserById(id);
			existingUser.setFname(userData.getFname());
			existingUser.setMname(userData.getMname());
			existingUser.setLname(userData.getLname());
			existingUser.setEmail(userData.getEmail());
			existingUser.setEmployementStatus(userData.getEmployementStatus());
			existingUser.setAddress(userData.getAddress());
			existingUser.setBirthdate(userData.getBirthdate());
			existingUser.setPhoneNumber(userData.getPhoneNumber());
			existingUser.setPosition(userData.getPosition());
			existingUser.setTerminationDate(userData.getTerminationDate());
			existingUser.setEditedBy(Editor);
			Role role = roleRepository.findByName(userData.getRole().getName());
			existingUser.setRole(role);
			System.out.println(role);
			System.out.println(userData.getRole().getName());
			staffRepository.save(existingUser);			
		}


		
}
