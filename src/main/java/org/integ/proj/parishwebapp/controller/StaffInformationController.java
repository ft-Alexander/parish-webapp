package org.integ.proj.parishwebapp.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.integ.proj.parishwebapp.dto.StaffDto;
import org.integ.proj.parishwebapp.entity.Role;
import org.integ.proj.parishwebapp.entity.Staff;
import org.integ.proj.parishwebapp.repository.RoleRepository;
import org.integ.proj.parishwebapp.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

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
	        // If no search is performed, retrieve all users and apply sorting
	        staffDto = staffService.findAllUsers();
	        Collections.sort(staffDto, Comparator.comparing(StaffDto::getFname));
	        model.addAttribute("staffDto", staffDto);
		    model.addAttribute("keyword", keyword);
		    return "users";
	    }

	    

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
//		Staff existingUser = staffService.findUserById(id);
//		existingUser.setFname(userData.getFname());
//		existingUser.setMname(userData.getMname());
//		existingUser.setLname(userData.getLname());
//		existingUser.setEmail(userData.getEmail());
//		existingUser.setEmployementStatus(userData.getEmployementStatus());
//		existingUser.setAddress(userData.getAddress());
//		existingUser.setBirthdate(userData.getBirthdate());
//		existingUser.setPhoneNumber(userData.getPhoneNumber());
//		existingUser.setPosition(userData.getPosition());
//		Role role = roleRepository.findByName(userData.getRole().getName());
//		existingUser.setRole(role);
//		System.out.println(role);
//		System.out.println(userData.getRole().getName());
//		
//		staffService.editUser(existingUser);
		staffService.editUser(userData, id);
		return "redirect:/users";
		
	}
	

    

//	@GetMapping("/users/{id}")
//	public String showDeleteConfirmationPage(@PathVariable("id") Long id, Model model) {
//	    model.addAttribute("userId", id);
//	    return "delete-confirmation";
//	}

	@GetMapping("/users/{id}")
	public String deleteUser(@PathVariable("id") Long id) {
		staffService.deleteUserById(id);
		return "redirect:/users";
	}
	@GetMapping("/users/view/{id}")
	public String viewUserForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("userData", staffService.findUserById(id));
		return "view-user";
	}

}
