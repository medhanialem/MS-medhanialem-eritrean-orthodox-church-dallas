package com.Medhanialem.service;

import com.Medhanialem.jwtauthentication.model.User;
import com.Medhanialem.model.PasswordDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
	

	UserDetails loadUserByUsername(String username);

	UserDetails getCurrentUserDetails();

	boolean checkIfValidOldPassword(User user, String oldPassword);

	void changeUserPassword(User user, String newPassword);

	User getUserByUsername(String username);

	boolean checkIfValidConfirmPassword(PasswordDto passwordDto);
}
