package org.integ.proj.parishwebapp.controller;

import java.security.Principal;
import java.util.List;

import org.integ.proj.parishwebapp.dto.StaffDto;
import org.integ.proj.parishwebapp.entity.Role;
import org.integ.proj.parishwebapp.entity.Staff;
import org.integ.proj.parishwebapp.repository.RoleRepository;
import org.integ.proj.parishwebapp.service.StaffService;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class StaffInformationController {
	private StaffService staffService;
	private RoleRepository roleRepository;

	public StaffInformationController(StaffService staffService, RoleRepository roleRepository) {
		super();
		this.staffService = staffService;
		this.roleRepository = roleRepository;
	}

	@GetMapping("/users")
	public String users(Model model,HttpServletRequest request) {
		List<StaffDto> staffDto = staffService.findAllUsers();
		model.addAttribute("staffDto", staffDto);
		 for (StaffDto dto : staffDto) {
		        System.out.println("Employment Date: " + dto.getEmployementDate());
		    }
		 Principal principal = request.getUserPrincipal();
			Staff staff = new Staff();
			staff = staffService.findUserByEmail(principal.getName());
			
			model.addAttribute("user",staff.getFname());
			System.out.println(staff.getFname());
		return "users";
	}
	@GetMapping("/users/search")
	public String search(Model model, @Param("keyword") String keyword) {
		List<Staff> listProducts = staffService.search(keyword);
		model.addAttribute("staffDto",listProducts);
		model.addAttribute("keyword",keyword);
		return "users";
	}

	// handler method to handle user information update request
	@GetMapping("/users/edit/{id}")
	public String editUserForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("userData", staffService.findUserById(id));
		return "edit-user";
	}

//	handler method to handle user information update submit request
	@PostMapping("/users/edit/{id}")
	public String editUser(@PathVariable("id") Long id, @ModelAttribute("userData") Staff userData,
			BindingResult result, Model model) {
		Staff existingUser = staffService.findUserById(id);
		existingUser.setFname(userData.getFname());
		existingUser.setMname(userData.getMname());
		existingUser.setLname(userData.getLname());
		existingUser.setEmail(userData.getEmail());
		existingUser.setEmployementStatus(userData.getEmployementStatus());

		Role role = roleRepository.findByName(userData.getRole().getName());
		existingUser.setRole(role);
		System.out.println(role);
		System.out.println(userData.getRole().getName());

		staffService.editUser(existingUser);
		return "redirect:/users";
	}

	@GetMapping("/users/{id}")
	public String deleteUser(@PathVariable("id") Long id) {
		staffService.deleteUserById(id);
		return "redirect:/users";
	}
	
	
}
