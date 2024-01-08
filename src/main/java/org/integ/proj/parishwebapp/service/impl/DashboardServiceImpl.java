package org.integ.proj.parishwebapp.service.impl;

import org.integ.proj.parishwebapp.repository.FinanceRepository;
import org.integ.proj.parishwebapp.repository.StaffRepository;
import org.integ.proj.parishwebapp.service.DashboardService;
import org.springframework.stereotype.Service;

@Service
public class DashboardServiceImpl implements DashboardService{
	 private StaffRepository staffRepository;
	 private FinanceRepository financeRepository;
	 

	public DashboardServiceImpl(StaffRepository staffRepository, FinanceRepository financeRepository) {
		super();
		this.staffRepository = staffRepository;
		this.financeRepository = financeRepository;
	}



	@Override
	public int getStaffCount() {
		return (int) staffRepository.count();
	}



	@Override
	public int getinvoiceCount() {
		return (int) financeRepository.count();
	}
	
	

}
