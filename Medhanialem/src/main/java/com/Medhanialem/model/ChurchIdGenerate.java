package com.Medhanialem.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ChurchIdGenerate {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long churchId;

	public Long getChurchId() {
		return churchId;
	}

	public void setChurchId(Long churchId) {
		this.churchId = churchId;
	}

	@Override
	public String toString() {
		return "ChurchIdGenerate [churchId=" + churchId + "]";
	}
}
