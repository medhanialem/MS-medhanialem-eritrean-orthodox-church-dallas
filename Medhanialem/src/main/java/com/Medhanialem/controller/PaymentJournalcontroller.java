package com.Medhanialem.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.Medhanialem.controller.model.payment.PaymentInformation;
import com.Medhanialem.controller.model.payment.PaymentLogs;
import com.Medhanialem.model.Member;
import com.Medhanialem.model.payment.MembershipPaymentLookupFee;
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
	public List<MembershipPaymentLookupFee> getPaymentLookupInfo(@PathVariable(value = "year") Long year) {
		return memberShipPaymentLookUpfee.getPaymentLookupInfo(year);
	}


	// This API will be used to post payment to DB

//	@PostMapping("/pay/{id}")
//	public void paymonthlyfee(@PathVariable(value = "id") Long id, @RequestBody Paymentreq request) {
//		double sum = 0;
//		Member member = memberRepository.findById(id).get();
//
//		List<MembershipPaymentLookupFee> unpaidMonths = getPaymentInformation(id);
//
//		Payment payment = new Payment();
//		payment.setMember(member);
//		payment.setTotal(request.getTotal());
//
//		payment = paymentRepository.save(payment);
//
//		for (MembershipPaymentLookupFee lookUp : unpaidMonths) {
//			// Member newmember = new Member();
//			// newmember = member;
//
//			PaymentJournal journal = new PaymentJournal();
//
//			double monthly = 0.0;
//			/*
//			 * if (member.getTier() == 1) { monthly = lookUp.getTeir1Amount(); } else if
//			 * (member.getTier() == 2) { monthly = lookUp.getTeir2Amount(); } else if
//			 * (member.getTier() == 3) { monthly = lookUp.getTeir3Amount(); } else { monthly
//			 * = lookUp.getTeir4Amount(); }
//			 */
//			journal.setAmount(monthly);
//
//			journal.setPaymentLookupfee(lookUp);
//			journal.setPayment(payment);
//			journal.setMember(member);
//
//			paymentJournalRepository.save(journal);
//
//			sum = sum + monthly;
//			if (sum >= request.getTotal()) {
//				break;
//			}
//		}
//	}


	
   //This method fetches payment information for all members for a selected year
	@GetMapping("/getallpayment/{year}")
	public List<PaymentInformation> getAllPayment(@PathVariable(value = "year") int year) {

		
		 List<Member> memberList = memberRepository.findAll().parallelStream().filter(t -> t.getSuperId() == 1)
		  .collect(Collectors.toList());

		List<PaymentInformation> list  = new ArrayList<>();

	
		List<PaymentlogDTO> listPaymentdto = paymentlogRepositoryjdbc.findAll( year);
		
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

			List<PaymentLogs> journals = getPaymentLogs(listPaymentdto,member);
			response.setPaymentLogs(journals);

			list.add(response);

		}

		return list;
	}

	private List<PaymentLogs> getPaymentLogs(List<PaymentlogDTO> listPaymentdto,Member member ) {

		List<PaymentLogs> paymentLoglist = new ArrayList<>();
		
		for (PaymentlogDTO paymentdto : listPaymentdto) {
			
			if(paymentdto.getMemberId().equals(member.getId())) {

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
