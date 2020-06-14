package com.Medhanialem.service.Impl;

import com.Medhanialem.exception.UserNotActiveException;
import com.Medhanialem.jwtauthentication.model.User;
import com.Medhanialem.model.PasswordDto;
import com.Medhanialem.repository.UserRepository;
import com.Medhanialem.service.UserPrinciple;
import com.Medhanialem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepository.findByUsername(username).orElseThrow(
				() -> new UsernameNotFoundException("User Not Found with -> username or email : " + username));
		if(!user.isActive()) {
			throw new UserNotActiveException("User Account", "username", user.getUsername());
		}
		return UserPrinciple.build(user);
	}

	@Override
	public UserDetails getCurrentUserDetails(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails currentUserDetails=null;
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			 currentUserDetails = (UserDetails) authentication.getPrincipal();
		}
		return currentUserDetails;
	}

	@Override
	public boolean checkIfValidOldPassword(User user, String oldPassword) {
		return passwordEncoder.matches(oldPassword, user.getPassword());
	}

	@Override
	public void changeUserPassword(User user, String newPassword) {
		user.setPassword(passwordEncoder.encode(newPassword));
		userRepository.save(user);
	}

	@Override
	public User getUserByUsername(String username) {
		User user = userRepository.findByUsername(username).orElseThrow(
				() -> new UsernameNotFoundException("User Not Found with -> username or email : " + username));
		return user;
	}

	@Override
	public boolean checkIfValidConfirmPassword(PasswordDto passwordDto) {
		return passwordDto.getNewPassword().equals(passwordDto.getConfirmPassword());
	}


}
