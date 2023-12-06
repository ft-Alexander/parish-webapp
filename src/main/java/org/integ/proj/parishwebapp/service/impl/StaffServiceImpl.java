package org.integ.proj.parishwebapp.service.impl;

import org.integ.proj.parishwebapp.dto.StaffDto;
import org.integ.proj.parishwebapp.entity.Role;
import org.integ.proj.parishwebapp.entity.Staff;
import org.integ.proj.parishwebapp.repository.RoleRepository;
import org.integ.proj.parishwebapp.repository.StaffRepository;
import org.integ.proj.parishwebapp.service.StaffService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
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
	        staff.setName(staffDto.getFname() + " " + staffDto.getMname()+" "+staffDto.getLname());
	        staff.setEmail(staffDto.getEmail());
	        // encrypt the password using spring security
	        staff.setPassword(passwordEncoder.encode(staffDto.getPassword()));
	        staff.setEmployementDate(staffDto.getEmployementDate());
	        Role role = roleRepository.findByName("USER");
	        if(role == null){
	            role = checkRoleExist();
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
	    
	    @Override
	    public Staff editUser(Staff staff) {
	    	return staffRepository.save(staff);
	    }
	    
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
	        return staffDto;
	    }

	    private Role checkRoleExist(){
	        Role role = new Role();
	        role.setName("ROLE_UNDEFINED");
	        return roleRepository.save(role);
	    }
}
