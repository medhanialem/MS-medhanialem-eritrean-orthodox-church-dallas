package com.Medhanialem.controller;

import com.Medhanialem.model.Member;
import com.Medhanialem.model.payment.Payment;
import com.Medhanialem.model.payment.PaymentLog;
import com.Medhanialem.model.payment.PaymentlogDTO;
import com.Medhanialem.model.payment.objects.*;
import com.Medhanialem.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PaymentJournalcontroller {

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	PaymentlogRepositoryjdbc paymentlogRepositoryjdbc;

	@Autowired
	PaymentRepository paymentRepository;

	@Autowired
	PaymentLogRepository paymentLogRepository;

	@Autowired
	PaymentLookUpRepository memberShipPaymentLookUpfee;


	// This API will be used to post payment to DB

	@PostMapping("/pay")
	public void paymonthlyfee(@RequestBody Paymentrequest paymentRequest) {

		Member member = memberRepository.findById(paymentRequest.getMemberId()).get();

		Payment payment = new Payment();
		payment.setMember(member);
		payment.setTotal(paymentRequest.getTotal());

		payment = paymentRepository.save(payment);

		for (MonthlyPaid monthlypaid : paymentRequest.getPayments()) {

			PaymentLog paymentLog = new PaymentLog();

			paymentLog.setAmount(monthlypaid.getAmount());

			paymentLog.setPayment(payment);
			paymentLog.setMember(member);
			paymentLog
					.setPaymentLookupfee(memberShipPaymentLookUpfee.findById(monthlypaid.getPaymentLookupId()).get());

			paymentLogRepository.save(paymentLog);
		}
	}

	// This method fetches payment information for all members for a selected year
	@GetMapping("/getallpayment/{year}")
	public List<PaymentInformation> getAllPayment(@PathVariable(value = "year") int year) {

		List<Member> memberList = memberRepository.findAll().parallelStream()
				.filter(t -> null==t.getParent())
				.collect(Collectors.toList());

		List<PaymentInformation> list = new ArrayList<>();

		List<PaymentlogDTO> listPaymentdto = paymentlogRepositoryjdbc.findAll(year);

		for (Member member : memberList) {
			PaymentInformation response = new PaymentInformation();

			response.setMemberId(member.getMemberId());
	//		response.setChurchId(member.getChurchId());
			response.setFirstName(member.getFirstName());
			response.setMiddleName(member.getMiddleName());
			response.setLastName(member.getLastName());
			response.setHomePhoneNo(member.getHomePhoneNo());
			response.setMemberId(member.getMemberId());
			response.setTier(member.getTier().getId());
			response.setRegistrationDate(member.getRegistrationDate());

			List<PaymentLogs> journals = getPaymentLogs(listPaymentdto, member);
			response.setPaymentLogs(journals);

			list.add(response);
		}
		return list;
	}

	private List<PaymentLogs> getPaymentLogs(List<PaymentlogDTO> listPaymentdto, Member member) {

		List<PaymentLogs> paymentLoglist = new ArrayList<>();

		for (PaymentlogDTO paymentdto : listPaymentdto) {

			if (paymentdto.getMemberId().equals(member.getMemberId())) {

				PaymentLogs paylog = new PaymentLogs();

				paylog.setPaymentLogId(paymentdto.getPaymentLogId());

				paylog.setMonth(paymentdto.getMonth());
				paylog.setYear(paymentdto.getYear());
				paylog.setAmount(paymentdto.getAmount());

				paymentLoglist.add(paylog);
			}
		}
		return paymentLoglist;
	}
}
