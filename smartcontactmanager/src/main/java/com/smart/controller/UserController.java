package com.smart.controller;

import java.io.File;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smart.dao.UserRepository;
import com.smart.entities.Contact;
import com.smart.entities.User;
import com.smart.helper.Message;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
//@EnableWebSecurity
public class UserController {
	
	@Autowired
	private UserRepository userRepository;

	@ModelAttribute
	public void commonMethod(Model m, Principal principal) {
		User user = userRepository.getUserByUserName(principal.getName());
		m.addAttribute("user", user);
	}
	@RequestMapping("/index")
//	@PreAuthorize("hasAuthority('ROLE_USER')")
	public String index(Model m, Principal principal) {
		
		return "normal/user_dashboard";
	}
	
	@RequestMapping("/add-contact")
	public String addContact(Model m) {
		m.addAttribute("title", "Add Contact");
		m.addAttribute("contact", new Contact());
		return "normal/add_contact_form";
	}
	@PostMapping("/handle-add-contact")
	public String handleContact(@ModelAttribute("contact") Contact contact,
								@RequestParam("profile") MultipartFile file,
								Principal principal,
								HttpSession session) {
		
		try {
//			boolean temp = false;
//			if(!temp) {
//				throw new Exception();
//			}
			
			if(file.isEmpty()) {
				System.out.println("Uploaded file is empty.");
			}else {
				System.out.println("Image name: "+file.getOriginalFilename());
				 File saveFile = new ClassPathResource("static/img").getFile();
				 Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
				 Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				 contact.setImage(file.getOriginalFilename());
				 System.out.println(path);
				 
				 session.setAttribute("message", new Message("Contact Added successfully!! Add more..", "success"));
			}
			
			
			User user = userRepository.getUserByUserName(principal.getName());
			user.getContacts().add(contact);
			contact.setUser(user);
			userRepository.save(user);
			
			
			
			
//			System.out.println("Contact Details: "+ contact);
//			is in any way we are trying to acesss to string method of user we getting exeption
//			System.out.println("User Deatils: "+ user);
//			why toString method of user giving problem?
		} catch (Exception e) {
			e.printStackTrace();
			//showing error message on view
			session.setAttribute("message", new Message("Something went wrong!! Please try again..", "danger"));
		}
		
		return "normal/add_contact_form";
	}
}
