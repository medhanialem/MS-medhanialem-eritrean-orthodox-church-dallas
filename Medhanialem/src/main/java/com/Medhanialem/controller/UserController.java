
package com.Medhanialem.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Medhanialem.jwtauthentication.model.User;
import com.Medhanialem.repository.UserRepository;


@RestController
public class UserController {

	Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserRepository userRepository;



	 @CrossOrigin(origins = "http://localhost:4200")
	// Get All Members
	@GetMapping("/users")
	public List<User> getAllMembers() {
		 return userRepository.findAll();

	 }



}
