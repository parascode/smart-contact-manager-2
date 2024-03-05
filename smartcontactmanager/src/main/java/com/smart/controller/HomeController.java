package com.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smart.dao.UserRepository;
import com.smart.entities.Contact;
import com.smart.entities.User;
import com.smart.helper.Message;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class HomeController {
	
	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping("/")
	public String home(Model model) {
		model.addAttribute("title", "Home : Smart Contact Manager");
		return "home";
	}
	
	@RequestMapping("/about")
	public String about(Model model) {
		model.addAttribute("title", "About : Smart Contact Manager");
		return "about";
	}
	@RequestMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("title", "Register : Smart Contact Manager");
//		User tempUser = new User();
//		tempUser.setName("MyUser");
//		tempUser.setAbout("description");
		model.addAttribute("user", new User());
		return "signup";
	}

	@RequestMapping(value = "/do_register", method = RequestMethod.POST)
	public String do_register(@Valid @ModelAttribute("user") User user, BindingResult result1,
			@RequestParam(name = "agreement", defaultValue = "false") boolean agreement,
			Model model, HttpSession session) {
		
		try {
			
			if(!agreement) {
				System.out.println("Please agree to terms and conditions");
				throw new Exception("Please agree to terms and conditions.");
//				session.setAttribute("message", new Message("Something went wromg! Please agree to terms and conditions.", "alert-warning"));
				
			}
			if(result1.hasErrors()) {
				System.out.println(result1.getAllErrors());
				return "signup";
			}
			
			user.setEnabled(true);
			user.setRole("ROLE_USER");
			user.setImageUrl("default.jpg");
			System.out.println("Agreement- " + agreement);
			System.out.println(user);
			
			User result = userRepository.save(user);
			model.addAttribute("user", new User());
			
			session.setAttribute("message", new Message("User registered successfully", "alert-success"));
			return "signup";
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("user", user);
			session.setAttribute("message", new Message("User registration failed - "+ e.getMessage(), "alert-danger"));
			return "signup";
		}
		
	}
//	@GetMapping("/test")
//	@ResponseBody
//	public String test() {
//		User user = new User();
//		user.setEmail("test@gmail.com");
//		user.setName("Test name");
//		user.setPassword("hello");
//		Contact contact = new Contact();
//		user.getContacts().add(contact);
//		userRepository.save(user);
//		return "This is for testing purpose only";
//	}
}
