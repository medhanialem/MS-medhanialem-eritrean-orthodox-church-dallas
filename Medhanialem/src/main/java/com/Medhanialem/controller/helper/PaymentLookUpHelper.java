package com.Medhanialem.controller.helper;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.Medhanialem.exception.BackendException;
import com.Medhanialem.model.payment.PaymentLookup;

import io.jsonwebtoken.lang.Collections;

@Component
public class PaymentLookUpHelper {
	
	private final Logger logger = LoggerFactory.getLogger(PaymentLookUpHelper.class.getName());

	public void validateLookUpCreationObject(List<PaymentLookup> paymentLookups) {
		
		Map<Integer, Boolean> monthRepeatCheck = new HashMap<>();
		
		//Initialize map
		for (int i = 1; i < 13; i++) {
			monthRepeatCheck.put(Integer.valueOf(i), Boolean.valueOf(false));
		}
		
		if (!Collections.isEmpty(paymentLookups) && paymentLookups.size() == 12) {
			int year = paymentLookups.get(0).getYear();
			paymentLookups.stream().forEach(p -> {
					
					// Payment lookup cannot be empty
					if (null == p) {
						logger.error("PaymentLookup cannot empty.");
						throw new BackendException("PaymentLookup cannot empty.");
					}
					
					// Check if same year
					if (year != p.getYear() ) {
						logger.error("PaymentLookup year cannot be different than the rest.");
						throw new BackendException("PaymentLookup year cannot be different than the rest.");
					}
					
					// Checking Month < 1
					if (p.getMonth() < 1) {
						logger.error("PaymentLookup month cannot be <1: {}", p.getMonth());
						throw new BackendException("PaymentLookup month cannot be <1: " + p.getMonth());
					}
					
					// Checking Month > 1
					if (p.getMonth() > 12) {
						logger.error("PaymentLookup month cannot be >12: " + p.getMonth());
						throw new BackendException("PaymentLookup month cannot be >12: " + p.getMonth());
					}
					
					
					//Checking Year
					Date today = new Date();
					Calendar cal = Calendar.getInstance();
					cal.setTime(today);
					int currentYear = cal.get(Calendar.YEAR);
					
					
					if (p.getYear() < currentYear) {
						logger.error("PaymentLookup year: {} cannot be < {}", p.getYear(), currentYear);
						throw new BackendException("PaymentLookup year: " +  p.getYear() + " cannot be < " + currentYear);
					}
					
					
					if (p.getYear() > currentYear + 1) {
						logger.error("PaymentLookup cannot empty.");
						throw new BackendException("PaymentLookup year: " +  p.getYear() + " cannot be > " + currentYear + 1);
					}
					
					//Checking Amount
					if (p.getAmount() < 0) {
						logger.error("PaymentLookup amount: {} cannot be < 0.", p.getAmount());
						throw new BackendException("PaymentLookup amount: "  + p.getAmount() + " cannot be < 0.");
					}
					
					//Check if month is repeated
					if (monthRepeatCheck.get(p.getMonth()) == true) {
						logger.error("PaymentLookup month: {} cannot be reapeated for the same year.", p.getMonth());
						throw new BackendException("PaymentLookup month: " + p.getMonth() + " cannot be reapeated for the same year.");
					} else {
						monthRepeatCheck.put(p.getMonth(), Boolean.valueOf(true));
					}
				});
			
		} else {
			logger.error("PaymentLookupList is empty or number of months is >12 or <12");
			throw new BackendException("PaymentLookupList is empty or number of months is >12 or <12");
		}
	}
}
