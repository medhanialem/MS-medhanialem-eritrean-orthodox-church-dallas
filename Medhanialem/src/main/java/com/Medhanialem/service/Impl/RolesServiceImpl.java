package com.Medhanialem.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Medhanialem.jwtauthentication.model.Role;
import com.Medhanialem.repository.RoleRepository;
import com.Medhanialem.service.RolesService;

@Service
public class RolesServiceImpl implements RolesService {
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public List<Role> getAllRoles() {
		return roleRepository.findAll();
	}

}
