
package com.Medhanialem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Medhanialem.model.payment.MembershipPaymentLookupFee;
import com.Medhanialem.repository.MemberShipPaymentLookUpfeeRepository;

@RestController
public class PaymentJournalcontroller {

//	@Autowired
//	MemberRepository memberRepository;

	@Autowired
	MemberShipPaymentLookUpfeeRepository memberShipPaymentLookUpfee;

	@GetMapping("/getpaymentinfo")
	public List<MembershipPaymentLookupFee> getMember() {

		// Optional<Member> member = memberRepository.findById(id);

		// memberShipPaymentLookUpfee.findAll();

		return memberShipPaymentLookUpfee.findpaylookUpfees();

	}

}
