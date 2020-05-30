package com.Medhanialem.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Medhanialem.controller.helper.PaymentLookUpHelper;
import com.Medhanialem.exception.BackendException;
import com.Medhanialem.model.payment.PaymentLookup;
import com.Medhanialem.model.payment.objects.PaymentLookupResponseByYear;
import com.Medhanialem.service.PaymentLookUpService;
import com.Medhanialem.utils.TypicalResponses;

@RestController
@RequestMapping("/paymentlookups")
public class PaymentLookUpController {
	
	private final Logger logger = LoggerFactory.getLogger(PaymentLookUpController.class.getName());
	
	@Autowired
	PaymentLookUpService paymentLookUpService;
	
	@Autowired
	PaymentLookUpHelper paymentLookUpHelper;
	
	@PostMapping("")
	@CrossOrigin(origins = "*")
	@PreAuthorize("hasAnyAuthority('ADMIN','SECRETARY','ABO_WENBER_SEBEKA_GUBAE')")
	public ResponseEntity<?> createPaymentLookUp(@RequestBody List<PaymentLookup> paymentLookups, @RequestParam(required = true) Long tierId) {
		
		logger.info("Inside createPaymentLookUp() method, {}", logger.getName());
		try {
			paymentLookUpHelper.validateLookUpCreationObject(paymentLookups);
		}
		catch (BackendException e) {
			logger.error(e.getMessage());
			return TypicalResponses.setError(e.getMessage());
		}
		return new ResponseEntity<>(this.paymentLookUpService.createPaymentLookUp(paymentLookups, tierId), HttpStatus.OK);
	}
	
	// The function of this API to update the PaymentLookup if there any change in the middle of the year cycle. 
	@PutMapping("")
	@CrossOrigin(origins = "*")
	@PreAuthorize("hasAnyAuthority('ADMIN','SECRETARY','ABO_WENBER_SEBEKA_GUBAE')")
	public ResponseEntity<?> upDatePaymentLookUp(@RequestBody List<PaymentLookup> paymentLookups) {
		
		logger.info("Inside upDatePaymentLookUp() method, {}", logger.getName());
		try {
			paymentLookUpHelper.validateLookUpUpDateObject(paymentLookups);
		}
		catch (BackendException e) {
			logger.error(e.getMessage());
			return TypicalResponses.setError(e.getMessage());
		}
		return new ResponseEntity<>(this.paymentLookUpService.upDatePaymentLookUp(paymentLookups), HttpStatus.OK);
	}
	

	//this Api fetches payment lookups for a selected tier and year
	@CrossOrigin(origins = "*")
	@PreAuthorize("hasAnyAuthority('ADMIN','SECRETARY','ABO_WENBER_SEBEKA_GUBAE')")
	@GetMapping("")
	public List<PaymentLookup> getPaymentLookupsByYearAndTier(@RequestParam("tierId") Long tierId, @RequestParam("year") Long year) {
		logger.info("Inside getPaymentLookupsByYearAndTier() method, {}", logger.getName());
		return paymentLookUpService.getPaymentLookupsByYearAndTier(tierId,year);
	}

	//this Api fetches payment lookups for all tiers for a selected year
	@CrossOrigin(origins = "*")
	@PreAuthorize("hasAnyAuthority('ADMIN','SECRETARY','ABO_WENBER_SEBEKA_GUBAE')")
	@GetMapping("/tiers/{year}")
	public List<PaymentLookupResponseByYear> getPaymentLookupInfo(@PathVariable(value = "year") Long year) {
		return paymentLookUpService.getPaymentLookupInfo(year);
	}

	//this Api fetches all payment lookups for a selected year to be viewed in a tiers page for updating
	@CrossOrigin(origins = "*")
	@PreAuthorize("hasAnyAuthority('ADMIN','SECRETARY','ABO_WENBER_SEBEKA_GUBAE')")
	@GetMapping("{year}")
	public List<PaymentLookup> getAllPaymentLookupByYear(@PathVariable(value = "year") Long year) {
		return paymentLookUpService.getAllPaymentLookupByYear(year);
	}

}
