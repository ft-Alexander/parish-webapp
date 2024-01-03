package org.integ.proj.parishwebapp.service;

import java.time.LocalDate;
import java.util.List;

import org.integ.proj.parishwebapp.dto.StaffDto;
import org.integ.proj.parishwebapp.entity.Staff;
import org.springframework.data.util.Streamable;

public interface StaffService {
	 void saveUser(StaffDto staffDto);
	 
	 void deleteUserById(Long id);

	 Staff findUserByEmail(String email);

	 List<StaffDto> findAllUsers();
	    
	 Staff findUserById(Long id);
	    
//	 Staff editUser(Staff staff);
	 
	 List<Staff> search(String keyword);
	 
	 void editUser(Staff staff, Long id);
}
