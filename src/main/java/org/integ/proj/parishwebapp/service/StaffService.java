package org.integ.proj.parishwebapp.service;

import java.util.List;

import org.integ.proj.parishwebapp.dto.StaffDto;
import org.integ.proj.parishwebapp.entity.Staff;

public interface StaffService {
	 void saveUser(StaffDto staffDto);

	    Staff findUserByEmail(String email);

	    List<StaffDto> findAllUsers();
}
