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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Medhanialem.controller.helper.PaymentLookUpHelper;
import com.Medhanialem.exception.BackendException;
import com.Medhanialem.model.payment.PaymentLookup;
import com.Medhanialem.service.PaymentLookUpService;
import com.medhaniealem.utils.TypicalResponses;

@RestController
@RequestMapping("/paymentLookUps")
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
		
		logger.info("Inside createMember() method, {}", logger.getName());
		try {
			paymentLookUpHelper.validateLookUpCreationObject(paymentLookups);
		}
		catch (BackendException e) {
			logger.error(e.getMessage());
			return TypicalResponses.setError(e.getMessage());
		}
		return new ResponseEntity<>(this.paymentLookUpService.createPaymentLookUp(paymentLookups, tierId), HttpStatus.OK);
		
	}

	@GetMapping("")
	@CrossOrigin(origins = "*")
	@PreAuthorize("hasAnyAuthority('ADMIN','SECRETARY','ABO_WENBER_SEBEKA_GUBAE')")
	public ResponseEntity<List<PaymentLookup>> getPaymentLookUp(@RequestParam("id") Long tierId, @RequestParam("year") Long year) {
		logger.info("Inside createMember() method, {}", logger.getName());
		return null;
	}
	
}
