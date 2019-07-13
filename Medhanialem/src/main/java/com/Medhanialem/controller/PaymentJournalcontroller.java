package com.Medhanialem.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.Medhanialem.model.Member;
import com.Medhanialem.model.payment.MembershipPaymentLookupFee;
import com.Medhanialem.repository.MemberRepository;
import com.Medhanialem.repository.MemberShipPaymentLookUpfeeRepository;


@RestController
public class PaymentJournalcontroller {

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	MemberShipPaymentLookUpfeeRepository memberShipPaymentLookUpfee;

	@GetMapping("/getpaymentinfo/{id}")
	public List<MembershipPaymentLookupFee> getPaymentInformation(@PathVariable(value = "id") Long id) {

		 Member member = memberRepository.findById(id).get();

		// memberShipPaymentLookUpfee.findAll();
		 
		 Long lookUpId = member.getMembershippaymentlookupfee().getId();
		 
		 
		int year = LocalDate.now().getYear();
		 
		int month = LocalDate.now().getMonthOfYear();
		 
		 

		return memberShipPaymentLookUpfee.findpaylookUpfees(lookUpId,year,month);

	}

}
