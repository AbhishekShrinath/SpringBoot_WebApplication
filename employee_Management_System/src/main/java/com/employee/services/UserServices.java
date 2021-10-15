package com.employee.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.employee.model.User;
import com.employee.web.dto.UserRegistrationDto;

public interface UserServices extends UserDetailsService {

	User save(UserRegistrationDto registrationDto); 
}
