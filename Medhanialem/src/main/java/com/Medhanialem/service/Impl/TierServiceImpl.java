package com.Medhanialem.service.Impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Medhanialem.exception.BackendException;
import com.Medhanialem.exception.ResourceNotFoundException;
import com.Medhanialem.model.payment.Tier;
import com.Medhanialem.repository.TierRepository;
import com.Medhanialem.service.TierService;

@Service
public class TierServiceImpl implements TierService {
	
	private final Logger logger = LoggerFactory.getLogger(TierServiceImpl.class.getName());
	
	@Autowired
	TierRepository tierRepository;
	
	@Autowired
	UserDetailsServiceImpl userDetailsServiceImpl;

	@Override
	public List<Tier> getAllTiers() {
		return tierRepository.findAll();
	}

	@Override
	public Tier createTier(Tier tierDetails) {
		logger.info("Inside createTier() method, {}", logger.getName());
		
		//Same tier description cannot be used in different tiers
		int count = this.tierRepository.countByDescription(tierDetails.getDescription());
		logger.info("Count of Tier with description {}: {}", tierDetails.getDescription(), count);
		if (count > 0) {
			throw new BackendException("Tier exists with same description: " + tierDetails.getDescription());
		}
		
		tierDetails.setTierType("Tier" + (this.tierRepository.getMaxTierId() + 1));
		tierDetails.setCreatedBy(userDetailsServiceImpl.getCurrentUserDetails().getUsername());
		System.out.println(tierDetails.getTierType());
		return this.tierRepository.save(tierDetails);
	}
	
	@Override
	public Tier getTierById(Long tierId) {
		logger.info("Inside getTierById() method, {}", logger.getName());
		return this.tierRepository.findById(tierId).orElseThrow(() -> new ResourceNotFoundException("Tier", "id", tierId));
	}

	@Override
	public Tier updateTier(Long tierId, Tier tierDetails) {
		logger.info("Inside updateTier() method, {}", logger.getName());
		Tier tier = this.tierRepository.findById(tierId).orElseThrow(() -> new ResourceNotFoundException("Tier", "id", tierId));
		int count = this.tierRepository.countByDescription(tierDetails.getDescription());
		logger.info("Count of Tier with description {}: {}", tierDetails.getDescription(), count);
		if (count > 0) {
			throw new BackendException("Tier exists with same description: " + tierDetails.getDescription());
		}
		tier.setDescription(tierDetails.getDescription());
		tier.setUpdatedBy(this.userDetailsServiceImpl.getCurrentUserDetails().getUsername());
		return this.tierRepository.save(tier);
	}

	@Override
	public void deleteTier(Long tierId) {
		logger.info("Inside deleteTier() method, {}", logger.getName());
		this.tierRepository.deleteById(tierId);
	}

}