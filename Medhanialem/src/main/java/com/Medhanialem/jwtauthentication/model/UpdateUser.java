package com.Medhanialem.jwtauthentication.model;

import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UpdateUser {
	
	 private Long id;
	 
	 @NotBlank
	 @Size(min=3, max = 50)
	 private String username;
	 
	 @NotNull
	 @Size(min=1)
	 private Set<String> roles;
	 
	 @NotNull
	 private boolean isActive;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return "UpdateUser [id=" + id + ", username=" + username + ", roles=" + roles + ", isActive=" + isActive + "]";
	}
	 
}
