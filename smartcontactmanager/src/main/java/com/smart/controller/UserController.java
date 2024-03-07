package com.smart.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smart.dao.UserRepository;
import com.smart.entities.User;

@Controller
@RequestMapping("/user")
//@EnableWebSecurity
public class UserController {
	
	@Autowired
	private UserRepository userRepository;

	@RequestMapping("/index")
//	@PreAuthorize("hasAuthority('ROLE_USER')")
	public String index(Model m, Principal principal) {
		User user = userRepository.getUserByUserName(principal.getName());
		m.addAttribute("user", user);
		return "normal/user_dashboard";
	}
}
