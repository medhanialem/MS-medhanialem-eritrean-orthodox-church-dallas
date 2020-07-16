package com.Medhanialem.jwtauthentication.model;

public class PasswordResetModel {
	private String enterNewPassword;
	private String confirmPassword;
	private String token;

	public String getEnterNewPassword() {
		return enterNewPassword;
	}

	public void setEnterNewPassword(String enterNewPassword) {
		this.enterNewPassword = enterNewPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
