package com.Medhanialem.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Medhanialem.controller.model.payment.MonthlyPaid;
import com.Medhanialem.controller.model.payment.PaymentInformation;
import com.Medhanialem.controller.model.payment.PaymentLogs;
import com.Medhanialem.controller.model.payment.PaymentLookUps;
import com.Medhanialem.controller.model.payment.PaymentLookupResponseByYear;
import com.Medhanialem.controller.model.payment.Paymentrequest;
import com.Medhanialem.model.Member;
import com.Medhanialem.model.payment.Payment;
import com.Medhanialem.model.payment.PaymentJournal;
import com.Medhanialem.model.payment.PaymentLookup;
import com.Medhanialem.model.payment.PaymentlogDTO;
import com.Medhanialem.repository.MemberRepository;
import com.Medhanialem.repository.MemberShipPaymentLookUpfeeRepository;
import com.Medhanialem.repository.PaymentJournalRepository;
import com.Medhanialem.repository.PaymentRepository;
import com.Medhanialem.repository.PaymentlogRepositoryjdbc;

@RestController
public class PaymentJournalcontroller {

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	PaymentlogRepositoryjdbc paymentlogRepositoryjdbc;

	@Autowired
	PaymentRepository paymentRepository;

	@Autowired
	PaymentJournalRepository paymentJournalRepository;

	@Autowired
	MemberShipPaymentLookUpfeeRepository memberShipPaymentLookUpfee;

	@GetMapping("/getpaymentlookupinfo/{year}")
	public List<PaymentLookupResponseByYear> getPaymentLookupInfo(@PathVariable(value = "year") Long year) {
		List<PaymentLookup> mplfList = memberShipPaymentLookUpfee.getPaymentLookupInfo(year);
		List<PaymentLookupResponseByYear> plrbyList = new ArrayList<>();

		for (int i = 1; i < 5; i++) {
			List<PaymentLookUps> plList = new ArrayList<>();
			PaymentLookupResponseByYear paymentLookUpResponseByYear = new PaymentLookupResponseByYear();
			for (PaymentLookup memberLookUpfee : mplfList) {

				if (memberLookUpfee.getTier().getId() == i) {
					PaymentLookUps paymentLookUp = new PaymentLookUps();
					paymentLookUp.setPaymentLookupId(memberLookUpfee.getId());
					paymentLookUp.setMonth(memberLookUpfee.getMonth());
					paymentLookUp.setYear(memberLookUpfee.getYear());
					paymentLookUp.setAmount(memberLookUpfee.getAmount());
					plList.add(paymentLookUp);

				}
			}
			paymentLookUpResponseByYear.setTierId((long) i);
			paymentLookUpResponseByYear.setPaymentLookUps(plList);
			plrbyList.add(paymentLookUpResponseByYear);
		}
		return plrbyList;
	}

	// This API will be used to post payment to DB

	@PostMapping("/pay")
	public void paymonthlyfee(@RequestBody Paymentrequest paymentRequest) {

		Member member = memberRepository.findById(paymentRequest.getMemberId()).get();

		Payment payment = new Payment();
		payment.setMember(member);
		payment.setTotal(paymentRequest.getTotal());

		payment = paymentRepository.save(payment);

		for (MonthlyPaid monthlypaid : paymentRequest.getPayments()) {

			PaymentJournal paymentJournal = new PaymentJournal();

			paymentJournal.setAmount(monthlypaid.getAmount());

			paymentJournal.setPayment(payment);
			paymentJournal.setMember(member);
			paymentJournal
					.setPaymentLookupfee(memberShipPaymentLookUpfee.findById(monthlypaid.getPaymentLookupId()).get());

			paymentJournalRepository.save(paymentJournal);
		}
	}

	// This method fetches payment information for all members for a selected year
	@GetMapping("/getallpayment/{year}")
	public List<PaymentInformation> getAllPayment(@PathVariable(value = "year") int year) {

		List<Member> memberList = memberRepository.findAll().parallelStream().filter(t -> t.getSuperId() == 1)
				.collect(Collectors.toList());

		List<PaymentInformation> list = new ArrayList<>();

		List<PaymentlogDTO> listPaymentdto = paymentlogRepositoryjdbc.findAll(year);

		for (Member member : memberList) {
			PaymentInformation response = new PaymentInformation();

			response.setMemberId(member.getId());
			response.setChurchId(member.getChurchId());
			response.setFirstName(member.getFirstName());
			response.setMiddleName(member.getMiddleName());
			response.setLastName(member.getLastName());
			response.setHomePhoneNo(member.getHomePhoneNo());
			response.setMemberId(member.getId());
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

			if (paymentdto.getMemberId().equals(member.getId())) {

				PaymentLogs paylog = new PaymentLogs();

				paylog.setPaymentLogId(paymentdto.getPaymentLogId());

				paylog.setMonth(paymentdto.getMonth());
				paylog.setYear(paymentdto.getYear());
				paylog.setAmount(paymentdto.getAmount());

				paymentLoglist.add(paylog);
			}
		}

		/*
		 * Comparator<PaymentLogs> journal_year_month_Comparator = Comparator
		 * .comparing(PaymentLogs::getMonth);
		 */

		/*
		 * Collections.sort(journallist, journal_year_month_Comparator);
		 */
		return paymentLoglist;

	}

}
