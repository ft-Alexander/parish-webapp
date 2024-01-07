package org.integ.proj.parishwebapp.controller;

<<<<<<< Updated upstream
=======
import java.math.BigDecimal;
import java.security.Principal;
>>>>>>> Stashed changes
import java.util.List;

import org.integ.proj.parishwebapp.entity.Finance;
import org.integ.proj.parishwebapp.dto.FinanceDto;
import org.integ.proj.parishwebapp.service.FinanceService;
<<<<<<< Updated upstream
=======
import org.integ.proj.parishwebapp.service.StaffService;
import org.springframework.data.repository.query.Param;
>>>>>>> Stashed changes
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FinanceController {
	private FinanceService financeService;
	
	public FinanceController(FinanceService financeService) {
		super();
		this.financeService = financeService;
	}
<<<<<<< Updated upstream
//	handler method to handle finance form
    @GetMapping("/users/finance")
    public String finance(Model model) {
    	List<FinanceDto> financeDto = financeService.findAllRecords();
    	model.addAttribute("financeDto", financeDto);
=======

	//	handler method to handle finance form
    @GetMapping("/finance")
    public String finance(Model model, HttpServletRequest request) {
    	List<Finance> finance = financeService.getAllFinancialRecordsOrderedByIdDesc();
    	model.addAttribute("financeDto", finance);
    	
    	Principal principal = request.getUserPrincipal();
		Staff staff = new Staff();
		staff = staffService.findUserByEmail(principal.getName());
		
		model.addAttribute("user",staff.getFname());
		System.out.println(staff.getFname());
>>>>>>> Stashed changes
    	return "finance";
    }
    
//	handler method to handle new financial record request
<<<<<<< Updated upstream
	@GetMapping("/users/finance/add-financial-record")
	public String addFinancialRecord(Model model) {
=======
	@GetMapping("/finance/add-financial-record")
	public String addFinancialRecord(Model model, HttpServletRequest request) {
>>>>>>> Stashed changes
		FinanceDto fRecord = new FinanceDto();
		if(financeService.getLastValueOfBalance() != null) {
			fRecord.setBalance(financeService.getLastValueOfBalance());
		}else {
			fRecord.setBalance(BigDecimal.valueOf(0.00));
		}
		
		Principal principal = request.getUserPrincipal();
		Staff staff = new Staff();
		staff = staffService.findUserByEmail(principal.getName());
		
		model.addAttribute("staff_id", staff);
		model.addAttribute("userData", fRecord);
		return "add-financial-record";
	}

<<<<<<< Updated upstream
//	handle method to handle new financial record submit request
	@PostMapping("users/finance/add-financial-record")
	public String addFinancialRecord(@ModelAttribute("fRecord") FinanceDto financeRecord, Model model) {
		financeService.addFinancialRecord(financeRecord);
		return "redirect:/users/finance";
=======
//	handler method to handle new financial record submit request
	@PostMapping("/finance/add-financial-record")
	public String addFinancialRecord(@ModelAttribute("userData") FinanceDto financeRecord, Model model, HttpServletRequest request) {
		Principal principal = request.getUserPrincipal();
		Staff staff = new Staff();
		staff = staffService.findUserByEmail(principal.getName());
		
		financeService.addFinancialRecord(financeRecord, staff.getId());
		return "redirect:/finance";
>>>>>>> Stashed changes
	}
	
//	handler method to handle financial record update request
	@GetMapping("/users/finance/edit-financial-record/{id}")
	public String editFinancialRecordForm(@PathVariable("id") Long id, Model model) {
		Finance fRecord = financeService.findFinancialRecordById(id);
    	BigDecimal previousBalance = financeService.getPreviousBalance(id);
    	fRecord.setBalance(previousBalance);
		model.addAttribute("userData", fRecord);
		return "edit-financial-record";
	}
	
//	handler method to handle financial record update submit request
	@PostMapping("/users/finance/edit-financial-record/{id}")
	public String editFinancialRecord(@PathVariable("id") Long id, @ModelAttribute("userData") Finance userData,
			BindingResult result, Model model, HttpServletRequest request) {
//		Finance existingRecord = financeService.findFinancialRecordById(id);
//		existingRecord.setNotes(userData.getNotes());
//		existingRecord.setDescription(userData.getDescription());
//		existingRecord.setDate(userData.getDate());
//		existingRecord.setTransaction_type(userData.getTransaction_type());
//		existingRecord.setAmount(userData.getAmount());
//		existingRecord.setBalance(userData.getBalance());
		
//		financeService.editFinancialRecord(existingRecord);
		Principal principal = request.getUserPrincipal();
		Staff staff = new Staff();
		staff = staffService.findUserByEmail(principal.getName());
		
		financeService.editFinancialRecord(userData, id, staff.getId());
		return "redirect:/finance";
	}
	
<<<<<<< Updated upstream
//	handler method to handle deletion of registered data
    @GetMapping("/users/finance/{id}")
=======
//	handler method to handle deletion of financial record
    @GetMapping("/finance/{id}")
>>>>>>> Stashed changes
    public String deleteUser(@PathVariable("id") Long id) {
    	financeService.deleteFinancialRecordById(id);
    	return "redirect:/finance";
    }
    
//	handler method to handle searching through existing records for a specific one
    @GetMapping("/finance/search")
    public String searchFinancialRecords(Model model, @Param("keyword") String keyword) {
    	List<Finance> searchResults = financeService.search(keyword);
    	model.addAttribute("financeDto", searchResults);
    	model.addAttribute("searchResults", keyword);
    	return "finance";
    }
    
//	handler method to handle viewing of financial record
    @GetMapping("/finance/view-financial-record/{id}")
    public String viewFinancialRecord(@PathVariable("id") Long id, Model model) {
    	model.addAttribute("userData", financeService.findFinancialRecordById(id));
    	return "view-financial-record";
    }
}
