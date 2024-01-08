package org.integ.proj.parishwebapp.controller;

import java.math.BigDecimal;
import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import org.integ.proj.parishwebapp.entity.Staff;
import org.integ.proj.parishwebapp.service.StaffService;
import org.integ.proj.parishwebapp.service.DashboardService;
import org.integ.proj.parishwebapp.service.FinanceService;

@Controller
public class DashboardController {
	
private DashboardService DashboardService;
private StaffService staffService;
private FinanceService financeService;
	
	
	

	public DashboardController(org.integ.proj.parishwebapp.service.DashboardService dashboardService,
		StaffService staffService, FinanceService financeService) {
	super();
	DashboardService = dashboardService;
	this.staffService = staffService;
	this.financeService = financeService;
}




	@GetMapping("/dashboard")
	public String showdashboard (Model model,HttpServletRequest request) {
		Principal principal = request.getUserPrincipal();
		Staff staff = new Staff();
		staff = staffService.findUserByEmail(principal.getName());
		
		model.addAttribute("user",staff.getFname());
		System.out.println(staff.getFname());
		
		int staffCount = DashboardService.getStaffCount(); // Implement this method in your service
	    model.addAttribute("staffCount", staffCount);
	    
	    int invoiceCount = DashboardService.getinvoiceCount();
	    model.addAttribute("invoiceCount", invoiceCount);
	    
	    BigDecimal budget = financeService.getLastValueOfBalance();
	    model.addAttribute("budget", budget);
	   
		return "dashboard";
	}
	
	
}
