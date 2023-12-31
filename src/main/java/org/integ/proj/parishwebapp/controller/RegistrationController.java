package org.integ.proj.parishwebapp.controller;

import java.util.List;

import org.integ.proj.parishwebapp.dto.StaffDto;
import org.integ.proj.parishwebapp.entity.Staff;
import org.integ.proj.parishwebapp.service.StaffService;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
public class RegistrationController {
	private StaffService staffService;
	
	
	public RegistrationController(StaffService staffService) {
		super();
		this.staffService = staffService;
	}
    @GetMapping("/register")
    public String showRegistrationForm1(Model model){
        // create model object to store form data
        StaffDto user = new StaffDto();
        model.addAttribute("user", user);
        return "register";
    }

//	handler method to handle user registration form submit request
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") StaffDto staffDto,
                               BindingResult result,
                               Model model){
        Staff existingUser = staffService.findUserByEmail(staffDto.getEmail());
        
        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
            		return "redirect:/register?error";
        }


        staffService.saveUser(staffDto);
        return "redirect:/register?success";
    }
    
    @GetMapping("/users")
    public String users(Model model,@Param("keyword") String keyword){
    		List<StaffDto> staffDto = staffService.findAllUsers();
    		model.addAttribute("staffDto", staffDto);
        return "users";
    } 
    
    // handler method to handle login request
    @GetMapping("/login")
    public String login() {
        return "index";
    }
}
