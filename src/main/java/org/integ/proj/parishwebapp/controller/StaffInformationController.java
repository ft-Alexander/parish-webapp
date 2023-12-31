package org.integ.proj.parishwebapp.controller;

import java.security.Principal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.integ.proj.parishwebapp.dto.StaffDto;
import org.integ.proj.parishwebapp.entity.Staff;
import org.integ.proj.parishwebapp.service.StaffService;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class StaffInformationController {
	private StaffService staffService;

	public StaffInformationController(StaffService staffService) {
		super();
		this.staffService = staffService;
	}

	@GetMapping("/users")
	public String users(Model model, HttpServletRequest request) {
		List<StaffDto> staffDto = staffService.findAllUsers();
		model.addAttribute("staffDto", staffDto);
		Principal principal = request.getUserPrincipal();
		Staff staff = new Staff();
		staff = staffService.findUserByEmail(principal.getName());

		model.addAttribute("user", staff.getFname());
		return "users";
	}

	@GetMapping("/users/search")
	public String search(Model model, @Param("keyword") String keyword) {
		List<Staff> listProducts = staffService.search(keyword);
		model.addAttribute("staffDto", listProducts);
		model.addAttribute("keyword", keyword);
		return "users";
	}

	@GetMapping("/users/ascFirstName")
	public String ascFirstName(Model model, @Param("keyword") String keyword) {
		if (keyword != null && StringUtils.hasText(keyword)) {
			List<Staff> listProducts = staffService.search(keyword);
			Collections.sort(listProducts, Comparator.comparing(Staff::getFname));
			model.addAttribute("staffDto", listProducts);
			model.addAttribute("keyword", keyword);
			return "users";
		} else {
			List<StaffDto> staffDto;
			staffDto = staffService.findAllUsers();
			Collections.sort(staffDto, Comparator.comparing(StaffDto::getFname));
			model.addAttribute("staffDto", staffDto);
			model.addAttribute("keyword", keyword);
			return "users";
		}

	}

	// handler method to handle user information update request
	@GetMapping("/users/edit/{id}")
	public String editUserForm(@PathVariable("id") Long id, Model model, HttpServletRequest request) {
		Principal principal = request.getUserPrincipal();
		Staff staff = new Staff();
		staff = staffService.findUserByEmail(principal.getName());

		model.addAttribute("EditedBy", staff);
		model.addAttribute("userData", staffService.findUserById(id));
		return "edit-user";
	}

	@PostMapping("/users/edit/{id}")
	public String editUser(@PathVariable("id") Long id, @ModelAttribute("userData") Staff userData,
			BindingResult result, Model model, HttpServletRequest request) {
		Principal principal = request.getUserPrincipal();
		Staff staff = new Staff();
		staff = staffService.findUserByEmail(principal.getName());

		model.addAttribute("EditedBy", staff.getId());
		staffService.editUser(userData, id, staff.getId());
		System.out.println(userData.getAddress());
		return "redirect:/users";

	}


	@GetMapping("/users/{id}")
	public String deleteUser(@PathVariable("id") Long id) {
		System.out.println(id);
		staffService.deleteUserById(id);
		return "redirect:/users";
	}

	@GetMapping("/users/view/{id}")
	public String viewUserForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("userData", staffService.findUserById(id));
		Staff staff = new Staff();
		System.out.println(staffService.findUserById(id).getEditedBy());
		if (staffService.findUserById(id).getEditedBy() != null) {
			staff = staffService.findUserById(staffService.findUserById(id).getEditedBy());
			model.addAttribute("EditedBy", staff);
			return "view-user";
		} else {
			staff = staffService.findUserById(id);
			model.addAttribute("EditedBy", staff);
			return "view-user";
		}

	}

}
