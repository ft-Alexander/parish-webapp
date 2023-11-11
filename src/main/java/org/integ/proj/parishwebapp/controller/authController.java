package org.integ.proj.parishwebapp.controller;

import java.util.List;

import org.integ.proj.parishwebapp.dto.StaffDto;
import org.integ.proj.parishwebapp.entity.Staff;
import org.integ.proj.parishwebapp.service.StaffService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

@Controller
public class authController {
	private StaffService staffService;
	
	
	public authController(StaffService staffService) {
		super();
		this.staffService = staffService;
	}
	// handler method to handle home page request
    @GetMapping("/index")
    public String home(){
        return "index";
    }
    // handler method to handle user registration form request
    @GetMapping("/register")
    public String showRegistrationForm1(Model model){
        // create model object to store form data
        StaffDto user = new StaffDto();
        model.addAttribute("user", user);
        return "register";
    }

    // handler method to handle user registration form submit request
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") StaffDto staffDto,
                               BindingResult result,
                               Model model){
        Staff existingUser = staffService.findUserByEmail(staffDto.getEmail());

        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

//        if(result.hasErrors()){
//            model.addAttribute("user", staffDto);
//            return "/register";
//        }

        staffService.saveUser(staffDto);
        return "redirect:/register?success";
    }
    
 // handler method to handle list of users
    @GetMapping("/users")
    public String users(Model model){
        List<StaffDto> staffDto = staffService.findAllUsers();
        model.addAttribute("staffDto", staffDto);
        return "users";
    } 
    // handler method to handle login request
    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
