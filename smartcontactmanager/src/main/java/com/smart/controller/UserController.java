package com.smart.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
//@EnableWebSecurity
public class UserController {

	@RequestMapping("/index")
//	@PreAuthorize("hasAuthority('ROLE_USER')")
	public String index() {
		return "normal/user_dashboard";
	}
}
