package com.Medhanialem.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Medhanialem.controller.model.payment.Journal;
import com.Medhanialem.controller.model.payment.PaymentResponse;
import com.Medhanialem.controller.model.payment.Paymentreq;
import com.Medhanialem.exception.ResourceNotFoundException;
import com.Medhanialem.model.Member;
import com.Medhanialem.model.payment.MembershipPaymentLookupFee;
import com.Medhanialem.model.payment.Payment;
import com.Medhanialem.model.payment.PaymentJournal;
import com.Medhanialem.repository.MemberRepository;
import com.Medhanialem.repository.MemberShipPaymentLookUpfeeRepository;
import com.Medhanialem.repository.PaymentJournalRepository;
import com.Medhanialem.repository.PaymentRepository;

@RestController
public class PaymentJournalcontroller {

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	PaymentRepository paymentRepository;
	
	@Autowired
	PaymentJournalRepository paymentJournalRepository;

	@Autowired
	MemberShipPaymentLookUpfeeRepository memberShipPaymentLookUpfee;

	@GetMapping("/getpaymentinfo/{id}")
	public List<MembershipPaymentLookupFee> getPaymentInformation(@PathVariable(value = "id") Long id) {

		Member member = memberRepository.findById(id).get();


		int year = LocalDate.now().getYear();

		int month = LocalDate.now().getMonthOfYear();
		
		
		List<PaymentJournal> journallist = paymentJournalRepository.getAllJournals(id);
		
		List<MembershipPaymentLookupFee> list=new ArrayList<>();
		
		if(null!=journallist && !journallist.isEmpty()) {
			 list = journallist.stream().map(m -> m.getPaymentLookupfee()).collect(Collectors.toList());
		}
		
	//	if(list.isEmpty() ) {throw new ResourceNotFoundException(null,"","");}
		 Long lookUpId;
		 
		if(null==journallist || journallist.isEmpty()) {
			int yearr = LocalDate.fromDateFields(member.getRegistrationDate()).getMonthOfYear();
			int mon = LocalDate.fromDateFields(member.getRegistrationDate()).getYear();

			MembershipPaymentLookupFee membershipPaymentLookupFee = memberShipPaymentLookUpfee.getIdbymonthAndyear(mon, yearr);
			 lookUpId = membershipPaymentLookupFee.getId();
		
		}else {
		
		   Comparator<MembershipPaymentLookupFee> paymentlookup_year_month_Comparator = Comparator.comparing(MembershipPaymentLookupFee::getYear)
		        .thenComparing(MembershipPaymentLookupFee::getMonth);
		   
		     Collections.sort(list,paymentlookup_year_month_Comparator);
		   
		    lookUpId =  list.get(list.size()-1).getId();
		}
		
		return memberShipPaymentLookUpfee.findpaylookUpfees(lookUpId, year, month);

	}

	@PostMapping("/pay/{id}")
	public void paymonthlyfee(@PathVariable(value = "id") Long id, @RequestBody Paymentreq request) {
		double sum = 0;
		Member member = memberRepository.findById(id).get();

		List<MembershipPaymentLookupFee> unpaidMonths = getPaymentInformation(id);

		Payment payment = new Payment();
		payment.setMember(member);
		payment.setTotal(request.getTotal());

		payment = paymentRepository.save(payment);
		
		for (MembershipPaymentLookupFee lookUp : unpaidMonths) {
            Member newmember = new Member();
            newmember = member;
            
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
			

			journal.setPaymentLookupfee(lookUp);
			journal.setPayment(payment);
			journal.setMember(newmember);
			
			
			paymentJournalRepository.save(journal);
			
			sum = sum + monthly;
			if (sum >= request.getTotal()) {
				break;
			}
		}
	}
	
	@GetMapping("/getAlljournals/{id}")
	public List<PaymentJournal> getAllJournals(@PathVariable(value = "id") Long id) {
		List<PaymentJournal> list =  paymentJournalRepository.getAllJournals(id);
		
		if(null == list || list.isEmpty()) {
			throw new ResourceNotFoundException("","No payment info for Member","");
		}
		 
		return list;
	}
	
	@GetMapping("/getallpayment")
	public List<PaymentResponse> getAllPayment() {
		
		List<Member> memberList = memberRepository.findAll();
		
		List<PaymentResponse> list = new ArrayList<>();
		
		for(Member member : memberList) {
			PaymentResponse response = new PaymentResponse();
			response.setFirstName(member.getFirstName());
			response.setMiddleName(member.getMiddleName());
			response.setLastName(member.getLastName());
			response.setMobile(member.getHomePhone());
			response.setMemberId(member.getId());
			response.setTier(member.getTier());
			response.setMemberSince(member.getRegistrationDate());
			
			List<Journal> journals = getPaymentJournals(member.getId());
			response.setJournalList(journals);
			
			
			list.add(response);
			
		}
			 
		return list;
	}

	private List<Journal> getPaymentJournals(Long id) {
		
		List<PaymentJournal> list =  paymentJournalRepository.getAllJournals(id);
		
		List<Journal> journallist = new ArrayList<>();
		
		for(PaymentJournal pjournal: list) {
			
			Journal journal = new Journal();
			
		//	journal.setTier(pjournal.);
			
			journal.setMonth(pjournal.getPaymentLookupfee().getMonth());
			journal.setYear(pjournal.getPaymentLookupfee().getYear());
			journal.setFee(pjournal.getAmount());
			journallist.add(journal);
		}
		Comparator<Journal> journal_year_month_Comparator = Comparator.comparing(Journal::getYear)
		        .thenComparing(Journal::getMonth);
		   
		     Collections.sort(journallist,journal_year_month_Comparator);
		
		return journallist;
		
		
	}

}
