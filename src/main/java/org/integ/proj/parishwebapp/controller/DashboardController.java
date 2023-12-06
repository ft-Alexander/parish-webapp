package org.integ.proj.parishwebapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {
	@GetMapping("/dashboard")
	public String showdashboard (Model model) {
		return "dashboard";
	}
}
