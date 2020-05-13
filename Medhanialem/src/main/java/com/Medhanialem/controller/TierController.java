
package com.Medhanialem.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.Medhanialem.model.payment.Tier;
import com.Medhanialem.service.TierService;


@RestController
@RequestMapping("/tiers")
public class TierController {

	Logger logger = LoggerFactory.getLogger(TierController.class);

	@Autowired
	TierService tierService;


	@CrossOrigin(origins = "*")
	@GetMapping("")
	@PreAuthorize("isAuthenticated()")
	public List<Tier> getAllTiers() {
		logger.info("Get All tiers method execution starts, {}", logger.getName());
		return tierService.getAllTiers();
	}

	@CrossOrigin(origins = "*")
	@PostMapping("")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'ABO_WENBER_SEBEKA_GUBAE')")
	public Tier createTier(@RequestBody Tier tierDetails) {
		logger.info("Inside createTier() method, {}", logger.getName());
		return this.tierService.createTier(tierDetails);

	}

	// Get one Tier
	@CrossOrigin(origins = "*")
	@GetMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'ABO_WENBER_SEBEKA_GUBAE')")
	public Tier getTier(@PathVariable(value = "id") Long tierId) {
		logger.info("Inside getTier() method, {}", logger.getName());
		return this.tierService.getTierById(tierId);

	}
	
	// Update a Tier
	@CrossOrigin(origins = "*")
	@PutMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'ABO_WENBER_SEBEKA_GUBAE')")
	public Tier updateTier(@PathVariable(value = "id") Long tierId, @Valid @RequestBody Tier tierDetails) {
		logger.info("Inside updateTier() method, {}", logger.getName());
		return this.tierService.updateTier(tierId, tierDetails);
		
	}

	// Delete a Tier
	@CrossOrigin(origins = "*")
	@DeleteMapping("/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	@PreAuthorize("hasAnyAuthority('ADMIN', 'ABO_WENBER_SEBEKA_GUBAE')")
	public void deleteTier(@PathVariable(value = "id") Long tierId) {
		logger.info("Inside deleteTier() method, {}", logger.getName());
		this.tierService.deleteTier(tierId);
		
	}

}
