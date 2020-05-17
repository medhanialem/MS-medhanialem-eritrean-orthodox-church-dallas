package com.Medhanialem.service.Impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.Medhanialem.model.payment.objects.PaymentLookUps;
import com.Medhanialem.model.payment.objects.PaymentLookupResponseByYear;
import io.jsonwebtoken.lang.Collections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Medhanialem.exception.BackendException;
import com.Medhanialem.exception.ResourceNotFoundException;
import com.Medhanialem.model.payment.PaymentLookup;
import com.Medhanialem.model.payment.Tier;
import com.Medhanialem.repository.PaymentLookUpRepository;
import com.Medhanialem.repository.TierRepository;
import com.Medhanialem.service.PaymentLookUpService;

@Service
public class PaymentLookUpServiceImpl implements PaymentLookUpService {

	private final Logger logger = LoggerFactory.getLogger(PaymentLookUpServiceImpl.class.getName());
	
	@Autowired
	PaymentLookUpRepository paymentLookUpRepository;
	
	@Autowired
	TierRepository tierRepository;

	@Autowired
	UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Override
	public List<PaymentLookup> createPaymentLookUp(List<PaymentLookup> paymentLookups, Long tierId) {
		logger.info("Inside createPaymentLookUp() method, {}", logger.getName());
		
//		int count = this.paymentLookUpRepository.countByTierIdMonthYear(paymentLookups.get(0).getTier().getId(), paymentLookups.get(0).getMonth(), paymentLookups.get(0).getYear());
//		logger.error("Count of Paymentlookup with TierId: {}, Month: {}, Year: {}", paymentLookups.get(0).getTier().getId(), paymentLookups.get(0).getMonth(), paymentLookups.get(0).getYear(), count);
//		if (count > 0) {
//			throw new BackendException("Paymentlookup exists with same TierId: " + paymentLookups.get(0).getTier().getId() + ", Month: " + paymentLookups.get(0).getMonth() + ", Year: " + paymentLookups.get(0).getYear() + " and count: " + count);
//		}
		
		Tier tier = this.tierRepository.findById(tierId).orElseThrow(() -> new ResourceNotFoundException("Tier", "id", tierId));
		
		paymentLookups.stream().forEach(
				p -> {
					p.setCreatedBy(userDetailsServiceImpl.getCurrentUserDetails().getUsername());
					p.setRevision(1);
					p.setTier(tier);
				});
		return this.paymentLookUpRepository.saveAll(paymentLookups);
	}

	@Override
	public List<PaymentLookUps> getPaymentLookupsByYearATier(Long tierId, Long year) {
		List<PaymentLookup> paymentLookupList = paymentLookUpRepository.findByTierAndYear(year, tierId);

		List<PaymentLookUps> paymentlookupsList = new ArrayList<>();

		if(!Collections.isEmpty(paymentLookupList)) {
			for (PaymentLookup paymentLookup : paymentLookupList) {
				if (paymentLookup.getTier().getId() == tierId) {
					PaymentLookUps paymentLookUps = new PaymentLookUps();
					paymentLookUps.setPaymentLookupId(paymentLookup.getId());
					paymentLookUps.setMonth(paymentLookup.getMonth());
					paymentLookUps.setYear(paymentLookup.getYear());
					paymentLookUps.setAmount(paymentLookup.getAmount());
					paymentlookupsList.add(paymentLookUps);
				}
			}
		}
		return paymentlookupsList;
	}

	@Override
	public List<PaymentLookupResponseByYear> getPaymentLookupInfo(Long year) {
		List<PaymentLookup> paymentLookupList = paymentLookUpRepository.findByYear(Math.toIntExact(year));

		List<Tier> tierList = tierRepository.findAll();

		List<PaymentLookupResponseByYear> paymentLookupResponseByYearList = new ArrayList<>();

		for (int i = 1; i < tierList.size(); i++) {
			List<PaymentLookUps> paymentLookUpsList = new ArrayList<>();
			PaymentLookupResponseByYear paymentLookUpResponseByYear = new PaymentLookupResponseByYear();
			for (PaymentLookup memberLookUpfee : paymentLookupList) {

				if (memberLookUpfee.getTier().getId() == i) {
					PaymentLookUps paymentLookUps = new PaymentLookUps();
					paymentLookUps.setPaymentLookupId(memberLookUpfee.getId());
					paymentLookUps.setMonth(memberLookUpfee.getMonth());
					paymentLookUps.setYear(memberLookUpfee.getYear());
					paymentLookUps.setAmount(memberLookUpfee.getAmount());
					paymentLookUpsList.add(paymentLookUps);

				}
			}
			paymentLookUpResponseByYear.setTierId((long) i);
			paymentLookUpResponseByYear.setPaymentLookUps(paymentLookUpsList);
			paymentLookupResponseByYearList.add(paymentLookUpResponseByYear);
		}
		return paymentLookupResponseByYearList;
	}

	@Override
	public List<PaymentLookup> getAllPaymentLookupByYear(Long year) {
		return paymentLookUpRepository.findByYear(Math.toIntExact(year));
	}

}
