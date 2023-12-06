package org.integ.proj.parishwebapp.controller;

import org.integ.proj.parishwebapp.entity.Staff;
import org.integ.proj.parishwebapp.service.StaffService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class StaffInformationController {
	private StaffService staffService;
	
	public StaffInformationController(StaffService staffService) {
		super();
		this.staffService = staffService;
	}
    
// 	handler method to handle user information update request
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
    	existingUser.setEmployementDate(userData.getEmployementDate());
    	
    	staffService.editUser(existingUser);
    	return "redirect:/users";
    }

//	handler method to handle deletion of registered data
    @GetMapping("/users/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
    	staffService.deleteUserById(id);
    	return "redirect:/users";
    }
}
