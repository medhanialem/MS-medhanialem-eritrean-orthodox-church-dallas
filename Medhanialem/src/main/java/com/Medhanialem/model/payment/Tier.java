package com.Medhanialem.model.payment;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Tier {

	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String TierType;
	
	@NotNull
	private String Description;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTierType() {
		return TierType;
	}

	public void setTierType(String tierType) {
		TierType = tierType;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}
	
	
	
	
	
	
}
