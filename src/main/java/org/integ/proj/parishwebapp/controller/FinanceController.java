package org.integ.proj.parishwebapp.controller;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import org.integ.proj.parishwebapp.entity.Finance;
import org.integ.proj.parishwebapp.entity.Staff;
import org.integ.proj.parishwebapp.dto.FinanceDto;
import org.integ.proj.parishwebapp.service.FinanceService;
import org.integ.proj.parishwebapp.service.StaffService;
import org.springframework.data.repository.query.Param;
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


	@GetMapping("/finance")
	public String finance(Model model, HttpServletRequest request) {
		List<Finance> finance = financeService.getAllFinancialRecordsOrderedByIdDesc();
		model.addAttribute("financeDto", finance);

		Principal principal = request.getUserPrincipal();
		Staff staff = new Staff();
		staff = staffService.findUserByEmail(principal.getName());

		model.addAttribute("user", staff.getFname());
		System.out.println(staff.getFname());

		return "finance";
	}



	@GetMapping("/finance/add-financial-record")
	public String addFinancialRecord(Model model, HttpServletRequest request) {

		FinanceDto fRecord = new FinanceDto();
		if (financeService.getLastValueOfBalance() != null) {
			fRecord.setBalance(financeService.getLastValueOfBalance());
		} else {
			fRecord.setBalance(BigDecimal.valueOf(0.00));
		}

		Principal principal = request.getUserPrincipal();
		Staff staff = new Staff();
		staff = staffService.findUserByEmail(principal.getName());

		model.addAttribute("staff_id", staff);
		model.addAttribute("userData", fRecord);
		return "add-financial-record";
	}



	@PostMapping("/finance/add-financial-record")
	public String addFinancialRecord(@ModelAttribute("userData") FinanceDto financeRecord, Model model,
			HttpServletRequest request) {
		Principal principal = request.getUserPrincipal();
		Staff staff = new Staff();
		staff = staffService.findUserByEmail(principal.getName());

		financeService.addFinancialRecord(financeRecord, staff.getId());
		return "redirect:/finance";

	}

	@GetMapping("/finance/edit-financial-record/{id}")
	public String editFinancialRecordForm(@PathVariable("id") Long id, Model model) {
		Finance fRecord = financeService.findFinancialRecordById(id);
		BigDecimal previousBalance = financeService.getPreviousBalance(id);
		fRecord.setBalance(previousBalance);
		model.addAttribute("userData", fRecord);
		return "edit-financial-record";
	}

	@PostMapping("/finance/edit-financial-record/{id}")
	public String editFinancialRecord(@PathVariable("id") Long id, @ModelAttribute("userData") Finance userData,
			BindingResult result, Model model, HttpServletRequest request) {
		Principal principal = request.getUserPrincipal();
		Staff staff = new Staff();
		staff = staffService.findUserByEmail(principal.getName());

		financeService.editFinancialRecord(userData, id, staff.getId());
		return "redirect:/finance";
	}


	@GetMapping("/finance/{id}")

	public String deleteUser(@PathVariable("id") Long id) {
		financeService.deleteFinancialRecordById(id);
		return "redirect:/finance";
	}

	@GetMapping("/finance/search")
	public String searchFinancialRecords(Model model, @Param("keyword") String keyword) {
		List<Finance> searchResults = financeService.search(keyword);
		model.addAttribute("financeDto", searchResults);
		model.addAttribute("searchResults", keyword);
		return "finance";
	}

	@GetMapping("/finance/view-financial-record/{id}")
	public String viewFinancialRecord(@PathVariable("id") Long id, Model model) {
		model.addAttribute("userData", financeService.findFinancialRecordById(id));
		return "view-financial-record";
	}
}
