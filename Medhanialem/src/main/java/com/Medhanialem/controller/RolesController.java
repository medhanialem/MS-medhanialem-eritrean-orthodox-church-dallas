package com.Medhanialem.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Medhanialem.jwtauthentication.model.Role;
import com.Medhanialem.service.RolesService;

@RestController
@RequestMapping("/api/roles")
public class RolesController {

	Logger logger = LoggerFactory.getLogger(RolesController.class);
	
	@Autowired
	private RolesService rolesService;
	
	@GetMapping("")
	@CrossOrigin(origins = "*")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'ABO_WENBER_SEBEKA_GUBAE')")
	public List<Role> getAllRoles() {
		logger.info("Inside getAllRoles() method, {}", logger.getName());
		 return rolesService.getAllRoles();

	 }
	
}