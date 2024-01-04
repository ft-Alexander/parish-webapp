package org.integ.proj.parishwebapp.service.impl;

import org.integ.proj.parishwebapp.repository.StaffRepository;
import org.integ.proj.parishwebapp.service.DashboardService;
import org.springframework.stereotype.Service;

@Service
public class DashboardServiceImpl implements DashboardService{
	 private StaffRepository staffRepository;
	 

	public DashboardServiceImpl(StaffRepository staffRepository) {
		super();
		this.staffRepository = staffRepository;
	}



	@Override
	public int getStaffCount() {
		return (int) staffRepository.count();
	}

}
