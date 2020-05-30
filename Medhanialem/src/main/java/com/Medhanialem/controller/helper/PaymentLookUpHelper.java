package com.Medhanialem.controller.helper;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.Medhanialem.exception.BackendException;
import com.Medhanialem.exception.ResourceNotFoundException;
import com.Medhanialem.model.payment.PaymentLookup;
import com.Medhanialem.repository.PaymentLookUpRepository;
import com.Medhanialem.repository.TierRepository;

import io.jsonwebtoken.lang.Collections;

@Component
public class PaymentLookUpHelper {
	
	private final Logger logger = LoggerFactory.getLogger(PaymentLookUpHelper.class.getName());
	@Autowired
	PaymentLookUpRepository paymentLookUpRepository;
	@Autowired
	TierRepository tierRepository;
	
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
					int currentYearPlusOne = currentYear + 1;
					
					
					if (p.getYear() < currentYear) {
						logger.error("PaymentLookup year: {} cannot be < {}", p.getYear(), currentYear);
						throw new BackendException("PaymentLookup year: " +  p.getYear() + " cannot be < " + currentYear);
					}
					
					
					if (p.getYear() > currentYearPlusOne) {
						logger.error("PaymentLookup cannot empty.");
						throw new BackendException("PaymentLookup year: " +  p.getYear() + " cannot be > " + currentYearPlusOne);
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
	
public void validateLookUpUpDateObject(List<PaymentLookup> paymentLookups) {
		
		Map<Integer, Boolean> monthRepeatCheck = new HashMap<>();
		
		//Initialize map
		for (int i = 0; i < paymentLookups.size(); i++) {
			monthRepeatCheck.put(Integer.valueOf(paymentLookups.get(i).getMonth()), Boolean.valueOf(false));
		}
		
		
		if (!Collections.isEmpty(paymentLookups) && paymentLookups.size() < 13) {
			Date today = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(today);
			int currentYear = cal.get(Calendar.YEAR);
			int currentYearPlusOne = currentYear + 1;
			
			Date date = new Date();
			LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			int month = localDate.getMonthValue();
			int minMonth= paymentLookups.get(0).getMonth();
			for(int i = 0; i < paymentLookups.size(); i++) {
				if(minMonth > paymentLookups.get(i).getMonth()) {
					minMonth = paymentLookups.get(i).getMonth();
				}
			}
           int minMonth1=minMonth;
			
			Long paymentLookupcurrnetId= paymentLookups.get(0).getId();
			int year = paymentLookups.get(0).getYear();
			PaymentLookup existingPaymentLookup = paymentLookUpRepository.findById(paymentLookupcurrnetId).orElseThrow(() -> new ResourceNotFoundException("PaymentLookup", "id", paymentLookupcurrnetId));
			int existingYear =existingPaymentLookup.getYear();
	    	Long tierId =  paymentLookups.get(0).getTier().getId();	//2
	    	Long oldTierId=existingPaymentLookup.getTier().getId();//2
	    	
	    	//
			if (tierId!=oldTierId) {
				logger.error("The new PaymentLookup Tier cannot be different from the existing tier");
				throw new BackendException("The new PaymentLookup Tier cannot be different from the existing tier : the new TierId:  "+ tierId +"  and the oldTierId: "+oldTierId);
			}
			//
			if (year!= existingYear) {
				logger.error("the new year and the existing year can't be different");
				throw new BackendException("the new year and the existing year can't be different: the new Year is " +  year + 
						" the existing Year is :  " + existingYear);
			}
		//Initialize map
		for (int i = minMonth1; i <=(minMonth1+paymentLookups.size()); i++) {
			monthRepeatCheck.put(Integer.valueOf(i), Boolean.valueOf(false));
		}
       
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
				
					// Check if same Tier
					if (tierId != p.getTier().getId()) {
						logger.error("PaymentLookup Tier cannot be different than the rest.");
						throw new BackendException("PaymentLookup Tier cannot be different than the rest.");
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
					if (p.getYear() < currentYear) {
						logger.error("PaymentLookup year: {} cannot be < {}", p.getYear(), currentYear);
						throw new BackendException("PaymentLookup year: " +  p.getYear() + " cannot be < " + currentYear);
					}		
					//
					if (p.getYear() > currentYearPlusOne) {
						logger.error("PaymentLookup cannot empty.");
						throw new BackendException("PaymentLookup year: " +  p.getYear() + " cannot be > " + currentYearPlusOne);
					}
					//
					if(currentYear==year && minMonth1<=month) {
						logger.error("You Not Make Change in the current or previous month the currnet is now  " +month);
						throw new BackendException("You Not Make Change in the current or previous month the currnet is now  " +month);
					}
					//
					int existingMonth =paymentLookUpRepository.findById(p.getId()).get().getMonth();
					int newDateMonth = p.getMonth();
					if(newDateMonth!=existingMonth) {
						logger.error(" the update month one of paymentlookup recoreds can't be different from the existing:  recordId  "+ paymentLookupcurrnetId+ " and update month: "
					+ month+  "and the exits month" + existingMonth);
						throw new BackendException(" the update month one of paymentlookup records can't be different from the existing:  recordId  "+ paymentLookupcurrnetId+ "  and the new month:  "
								+ newDateMonth+  "  and the exits month:  " + existingMonth);
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
