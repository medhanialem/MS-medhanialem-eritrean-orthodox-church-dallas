package com.Medhanialem.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.Medhanialem.jwtauthentication.model.User;
import com.Medhanialem.model.PasswordDto;

@Service
public interface UserService {
	

	UserDetails loadUserByUsername(String username);

	UserDetails getCurrentUserDetails();

	boolean checkIfValidOldPassword(User user, String oldPassword);

	void changeUserPassword(User user, String newPassword);

	User getUserByUsername(String username);

	boolean checkIfValidConfirmPassword(PasswordDto passwordDto);
	boolean requestPasswordReset(String email);

	boolean resetPassword(String token, String password) ;
}
