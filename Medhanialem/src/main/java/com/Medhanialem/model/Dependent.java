package com.Medhanialem.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "dependents")
public class Dependent implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	private String dependentName;

	@NotBlank
	private String dependentBaptismName;

	@NotBlank
	private String memberId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDependentName() {
		return dependentName;
	}

	public void setDependentName(String dependentName) {
		this.dependentName = dependentName;
	}

	public String getDependentBaptismName() {
		return dependentBaptismName;
	}

	public void setDependentBaptismName(String dependentBaptismName) {
		this.dependentBaptismName = dependentBaptismName;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	@Override
	public String toString() {
		return "Dependent [id=" + id + ", dependentName=" + dependentName + ", dependentBaptismName="
				+ dependentBaptismName + ", memberId=" + memberId + "]";
	}
}
