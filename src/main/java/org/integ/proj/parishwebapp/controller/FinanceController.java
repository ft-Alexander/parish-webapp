package org.integ.proj.parishwebapp.controller;

import java.security.Principal;
import java.util.List;

import org.integ.proj.parishwebapp.entity.Finance;
import org.integ.proj.parishwebapp.entity.Staff;
import org.integ.proj.parishwebapp.dto.FinanceDto;
import org.integ.proj.parishwebapp.service.FinanceService;
import org.integ.proj.parishwebapp.service.StaffService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class FinanceController {
	private FinanceService financeService;
	private StaffService staffService;
	
	
public FinanceController(FinanceService financeService, StaffService staffService) {
		super();
		this.financeService = financeService;
		this.staffService = staffService;
	}

	//	handler method to handle finance form
    @GetMapping("/finance")
    public String finance(Model model, HttpServletRequest request) {
    	List<FinanceDto> financeDto = financeService.findAllRecords();
    	model.addAttribute("financeDto", financeDto);
    	
    	Principal principal = request.getUserPrincipal();
		Staff staff = new Staff();
		staff = staffService.findUserByEmail(principal.getName());
		
		model.addAttribute("user",staff.getFname());
		System.out.println(staff.getFname());
    	return "finance";
    }
    
//	handler method to handle new financial record request
	@GetMapping("/finance/add-financial-record")
	public String addFinancialRecord(Model model) {
		FinanceDto fRecord = new FinanceDto();
		model.addAttribute("fRecord", fRecord);
		return "add-financial-record";
	}

//	handle method to handle new financial record submit request
	@PostMapping("/finance/add-financial-record")
	public String addFinancialRecord(@ModelAttribute("fRecord") FinanceDto financeRecord, Model model) {
		financeService.addFinancialRecord(financeRecord);
		return "redirect:/users/finance";
	}
	
//	handler method to handle financial record update request
	@GetMapping("/finance/edit-financial-record/{id}")
	public String editFinancialRecordForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("userData", financeService.findFinancialRecordById(id));
		return "edit-financial-record";
	}
	
//	handler method to handle financial record update submit request
	@PostMapping("/finance/edit-financial-record/{id}")
	public String editFinancialRecord(@PathVariable("id") Long id, @ModelAttribute("userData") Finance userData,
			BindingResult result, Model model) {
		Finance existingRecord = financeService.findFinancialRecordById(id);
		existingRecord.setNotes(userData.getNotes());
		existingRecord.setDescription(userData.getDescription());
		existingRecord.setDate(userData.getDate());
		existingRecord.setIncome(userData.getIncome());
		existingRecord.setExpense(userData.getExpense());
		existingRecord.setBalance(userData.getBalance());
		
		financeService.editFinancialRecord(existingRecord);
		return "redirect:/users/finance";
	}
	
//	handler method to handle deletion of registered data
    @GetMapping("/finance/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
    	financeService.deleteFinancialRecordById(id);
    	return "redirect:/users";
    }
}
