package com.Medhanialem.controller;

import java.util.List;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Medhanialem.exception.BackendException;
import com.Medhanialem.model.payment.objects.PaymentInformation;
import com.Medhanialem.model.payment.objects.PaymentResponse;
import com.Medhanialem.model.payment.objects.Paymentrequest;
import com.Medhanialem.service.PaymentJournalService;
import com.Medhanialem.utils.TypicalResponses;

@RestController
@CrossOrigin(origins = "*")
public class PaymentJournalcontroller {
	
	Logger logger = LoggerFactory.getLogger(PaymentJournalcontroller.class);
	
	@Autowired
	PaymentJournalService paymentJournalService;

	// This API will be used to post payment to DB
	@PostMapping("/pay")
	@PreAuthorize("hasAnyAuthority('ADMIN','SECRETARY','ABO_WENBER_SEBEKA_GUBAE')")
	public ResponseEntity<?> payMonthlyFee(@RequestBody @Valid Paymentrequest paymentRequest) {
		
		logger.info("Inside payMonthlyFee() method, {}", logger.getName());
		PaymentResponse insertedPayment;
		
		try {
			insertedPayment = paymentJournalService.payMonthlyFee(paymentRequest);
		}
		catch (BackendException e) {
			logger.error(e.getMessage());
			return TypicalResponses.setError(e.getMessage());
		}
		
		return new ResponseEntity<PaymentResponse>(insertedPayment, HttpStatus.CREATED);
		
	}

	// This method fetches payment information for all members for a selected year
	@GetMapping("/getallpayment/{year}")
	@PreAuthorize("hasAnyAuthority('ADMIN','SECRETARY','ABO_WENBER_SEBEKA_GUBAE')")
	public List<PaymentInformation> getAllPayment(@PathVariable(value = "year") int year) {
		
		logger.info("Inside getAllPayment() method, {}", logger.getName());
		return paymentJournalService.getAllPayment(year);
		
	}
	
	@GetMapping("/previousYearPaymentExist")
	@PreAuthorize("hasAnyAuthority('ADMIN','SECRETARY','ABO_WENBER_SEBEKA_GUBAE')")
	public boolean unpaidPreviousYearPaymentExist(@RequestParam("memberId") Long memberId, @RequestParam("paymentStartYear") Long paymentStartYear, @RequestParam("year") Long year) {
		
		logger.info("Inside unpaidPreviousYearPaymentExist() method, {}", logger.getName());
		return paymentJournalService.unpaidPreviousYearPaymentExist(memberId, paymentStartYear, year);
		
	}
	
}
