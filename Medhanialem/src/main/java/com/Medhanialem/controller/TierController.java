
package com.Medhanialem.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Medhanialem.model.payment.Tier;
import com.Medhanialem.repository.TierRepository;


@RestController
public class TierController {

	Logger logger = LoggerFactory.getLogger(TierController.class);
	
	@Autowired
	TierRepository tierRepository;



	 @CrossOrigin(origins = "http://localhost:4200")
	// Get All Members
	@GetMapping("/tiers")
	public List<Tier> getAllMembers() {
		return tierRepository.findAll();
	}



}
