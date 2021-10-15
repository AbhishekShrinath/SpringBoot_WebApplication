package com.employee.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.employee.services.UserServices;
import com.employee.web.dto.UserRegistrationDto;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {

	private UserServices services;

	public UserRegistrationController(UserServices services) {
		super();
		this.services = services;
	}
	
	@ModelAttribute("user")
	public UserRegistrationDto dto() {
		return new UserRegistrationDto();
	}
	@GetMapping
	public String ShowRegistration() {
		return "registration";
	}
	
	@PostMapping
	public String registoruserAccount(@ModelAttribute("user") UserRegistrationDto dto) {
		services.save(dto);
		
		return "redirect:/registration?Success";
	}
}
