package com.Medhanialem.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Medhanialem.controller.model.payment.Paymentreq;
import com.Medhanialem.model.Member;
import com.Medhanialem.model.payment.MembershipPaymentLookupFee;
import com.Medhanialem.model.payment.Payment;
import com.Medhanialem.model.payment.PaymentJournal;
import com.Medhanialem.repository.MemberRepository;
import com.Medhanialem.repository.MemberShipPaymentLookUpfeeRepository;
import com.Medhanialem.repository.PaymentRepository;

@RestController
public class PaymentJournalcontroller {

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	PaymentRepository paymentRepository;

	@Autowired
	MemberShipPaymentLookUpfeeRepository memberShipPaymentLookUpfee;

	@GetMapping("/getpaymentinfo/{id}")
	public List<MembershipPaymentLookupFee> getPaymentInformation(@PathVariable(value = "id") Long id) {

		Member member = memberRepository.findById(id).get();

		Long lookUpId = member.getLastpaymentReference();

		if (lookUpId == 0) {

			int year = LocalDate.fromDateFields(member.getRegistrationDate()).getMonthOfYear();
			int month = LocalDate.fromDateFields(member.getRegistrationDate()).getYear();

			MembershipPaymentLookupFee membershipPaymentLookupFee = memberShipPaymentLookUpfee
					.getIdbymonthAndyear(month, year);
			lookUpId = membershipPaymentLookupFee.getId();
		}
		int year = LocalDate.now().getYear();

		int month = LocalDate.now().getMonthOfYear();

		return memberShipPaymentLookUpfee.findpaylookUpfees(lookUpId, year, month);

	}

	@PostMapping("/pay/{id}")
	public void paymonthlyfee(@PathVariable(value = "id") Long id, @RequestBody Paymentreq request) {
		double sum = 0;
		Member member = memberRepository.findById(id).get();

		List<MembershipPaymentLookupFee> unpaidMonths = getPaymentInformation(id);

		Payment payment = new Payment();
		payment.setMemberId(request.getMemberId());
		payment.setTotal(request.getTotal());
		Long lastId = null;
		Set<PaymentJournal> paymentJournals = new HashSet<>();

		for (MembershipPaymentLookupFee lookUp : unpaidMonths) {

			PaymentJournal journal = new PaymentJournal();

			double monthly = 0.0;
			if (member.getTier() == 1) {
				monthly = lookUp.getTeir1Amount();
			} else if (member.getTier() == 2) {
				monthly = lookUp.getTeir2Amount();
			} else if (member.getTier() == 3) {
				monthly = lookUp.getTeir3Amount();
			} else {
				monthly = lookUp.getTeir4Amount();
			}
			journal.setAmount(monthly);
			journal.setMembershipId(payment.getMemberId());

			journal.setPaymentLookupfee(lookUp);
			journal.setPayment(payment);

			member.setLastpaymentReference(lookUp.getId());

			paymentJournals.add(journal);

			sum = sum + monthly;
			if (sum >= request.getTotal()) {
				break;
			}
		}

		payment.setJournals(paymentJournals);

		memberRepository.save(member);
		paymentRepository.save(payment);

	}

}
