package com.Medhanialem.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Medhanialem.model.payment.Payment;
import com.Medhanialem.model.payment.PaymentJournal;
import com.Medhanialem.model.payment.PaymentJrequest;
import com.Medhanialem.model.payment.Tier;
import com.Medhanialem.repository.PaymentJournalRepository;
import com.Medhanialem.repository.TiersRepository;

@RestController
public class PaymentJournalcontroller {

	Logger logger = LoggerFactory.getLogger(PaymentJournal.class);

	@Autowired
	PaymentJournalRepository paymentjournalRepository;
	
	@Autowired
	TiersRepository tiersRepository;
	

	@PostMapping("/makepayment")
	public PaymentJournal createMember(@Valid @RequestBody PaymentJrequest paymentJournal) {
		List<String> tiers = paymentJournal.getTiers();
		
		
	Tier t = new Tier();	
		
	List<Tier> tierslist =  tiersRepository.findAll();
	
	for(Tier tier: tierslist) {
		if(tiers.get(0) != null && tiers.get(0).equals(tier.getTiername())) {
			t.setTiername(tier.getTiername());
		}
	}
		PaymentJournal journal = new PaymentJournal();
		
		Set<Tier> tieradded = new HashSet<>();
		tieradded.add(t);
		
		journal.setTiers(tieradded);
		journal.setAmount(paymentJournal.getAmount());
		journal.setMembershipId(paymentJournal.getMembershipId());
		
		Payment p = new Payment();
		p.setMemberId(paymentJournal.getMembershipId());
		p.setTotal(paymentJournal.getAmount());
		
		journal.setPayment(p);
		
		 return paymentjournalRepository.save(journal);
		
	}

}
