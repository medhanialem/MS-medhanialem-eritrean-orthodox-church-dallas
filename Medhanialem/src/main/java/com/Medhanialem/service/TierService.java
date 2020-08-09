package com.Medhanialem.service;

import java.util.List;

import com.Medhanialem.model.payment.Tier;

public interface TierService {

	public List<Tier> getAllTiers();
	
	public Tier createTier(Tier tierDetails);
	
	public Tier getTierById(Long tierId);
	
	public Tier updateTier(Long tierId, Tier tierDetails);
	
	public boolean deleteTier(Long tierId);
	
}
