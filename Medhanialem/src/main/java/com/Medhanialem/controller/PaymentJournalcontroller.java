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

import com.Medhanialem.model.Member;
import com.Medhanialem.model.payment.Payment;
import com.Medhanialem.model.payment.PaymentJournal;
import com.Medhanialem.model.payment.PaymentLookup;
import com.Medhanialem.model.payment.PaymentlogDTO;
import com.Medhanialem.model.payment.objects.MonthlyPaid;
import com.Medhanialem.model.payment.objects.PaymentInformation;
import com.Medhanialem.model.payment.objects.PaymentLogs;
import com.Medhanialem.model.payment.objects.PaymentLookUps;
import com.Medhanialem.model.payment.objects.PaymentLookupResponseByYear;
import com.Medhanialem.model.payment.objects.Paymentrequest;
import com.Medhanialem.repository.MemberDAOjdbc;
import com.Medhanialem.repository.MemberRepository;
import com.Medhanialem.repository.MemberShipPaymentLookUpfeeRepository;
import com.Medhanialem.repository.PaymentJournalRepository;
import com.Medhanialem.repository.PaymentRepository;

@RestController
public class PaymentJournalcontroller {

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	MemberDAOjdbc memberDAOjdbc;

	@Autowired
	PaymentRepository paymentRepository;

	@Autowired
	PaymentJournalRepository paymentJournalRepository;

	@Autowired
	MemberShipPaymentLookUpfeeRepository memberShipPaymentLookUpfee;

	@GetMapping("/getpaymentlookupinfo/{year}/{tier}")
	public List<PaymentLookUps> getPaymentLookupInfoByTier(@PathVariable(value = "year") Long year,
			@PathVariable(value = "tier") Long tier) {
		List<PaymentLookup> pList = memberShipPaymentLookUpfee.getPaymentLookupInfoByTier(year, tier);
		List<PaymentLookUps> plookupsList = new ArrayList<>();
		for (PaymentLookup pLookUp : pList) {
			if (pLookUp.getTier().getId() == tier) {
				PaymentLookUps paymentLookUp = new PaymentLookUps();
				paymentLookUp.setPaymentLookupId(pLookUp.getId());
				paymentLookUp.setMonth(pLookUp.getMonth());
				paymentLookUp.setYear(pLookUp.getYear());
				paymentLookUp.setAmount(pLookUp.getAmount());
				plookupsList.add(paymentLookUp);
			}
		}
		return plookupsList;
	}

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

		List<Member> memberList = memberRepository.findAll().parallelStream()
				.filter(t -> null==t.getParent())
				.collect(Collectors.toList());

		List<PaymentInformation> list = new ArrayList<>();

		List<PaymentlogDTO> listPaymentdto = memberDAOjdbc.findAll(year);

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
