package com.employee.services;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.employee.model.Role;
import com.employee.model.User;
import com.employee.repository.UserRepository;
import com.employee.web.dto.UserRegistrationDto;

@Service
public class UserServicesIMPL implements UserServices {

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	public UserServicesIMPL(UserRepository repository) {
		super();
		this.repository = repository;
	}


	@Override
	public User save(UserRegistrationDto registrationDto) {
		User user=new User(registrationDto.getFirstname(),registrationDto.getLastname(),
				registrationDto.getEmail(),
				bCryptPasswordEncoder.encode(registrationDto.getPassword()),Arrays.asList(new Role("ROLE_USER")));
		
		return repository.save(user);
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user=repository.findByEmail(username);
		if(user==null) {
			throw new UsernameNotFoundException("User Not Found");
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),mapRoleToAuthorities(user.getRoles()));
	}
	
	private Collection<? extends GrantedAuthority> mapRoleToAuthorities(Collection<Role> roles){
	
		return 	roles.stream().map(role-> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

}
