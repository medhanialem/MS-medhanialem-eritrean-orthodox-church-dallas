package com.Medhanialem.service.Impl;

import java.util.List;

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

}
