package com.Medhanialem.service.Impl;

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

import com.Medhanialem.exception.UserNotActiveException;
import com.Medhanialem.jwtauthentication.model.PasswordResetTokenEntity;
import com.Medhanialem.jwtauthentication.model.User;
import com.Medhanialem.jwtauthentication.security.jwt.JwtProvider;
import com.Medhanialem.model.PasswordDto;
import com.Medhanialem.repository.MemberRepository;
import com.Medhanialem.repository.PasswordResetTokenRepository;
import com.Medhanialem.repository.UserRepository;
import com.Medhanialem.service.UserPrinciple;
import com.Medhanialem.service.UserService;
import com.Medhanialem.utils.ResetPasswordByEmailUtility;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

	@Autowired
	UserRepository userRepository;
	
	
	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private JwtProvider jwtProvider;

	@Autowired
	private PasswordResetTokenRepository passwordResetTokenRepository;

	@Autowired
	private ResetPasswordByEmailUtility emailUtility;

	@Autowired
	PasswordEncoder encoder;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		//User user = userRepository.findByUsername(username).orElseThrow(
				//() -> new UsernameNotFoundException("User Not Found with -> username or email : " + username));
		User user = userRepository.findByUsername(username);
		if (user==null) {
		
	     throw	new UsernameNotFoundException("User Not Found with -> username or email : " + username);
		}
		if (!user.isActive()) {
			throw new UserNotActiveException("User Account", "username", user.getUsername());
		}
		return UserPrinciple.build(user);
	}

	@Override
	public UserDetails getCurrentUserDetails() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails currentUserDetails = null;
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
		//User user = userRepository.findByUsername(username).orElseThrow(
			//	() -> new UsernameNotFoundException("User Not Found with -> username or email : " + username));
		User user = userRepository.findByUsername(username);
				if (user==null) {
				
			     throw	new UsernameNotFoundException("User Not Found with -> username or email : " + username);
				}
				return user;
	}

	@Override
	public boolean checkIfValidConfirmPassword(PasswordDto passwordDto) {
		return passwordDto.getNewPassword().equals(passwordDto.getConfirmPassword());
	}

	@Override
	public boolean requestPasswordReset(String email) {
		boolean returnValue = false;
		/// User userEntity= userRepository.findByEmail(email);
		// Member memberEntity= memberRepository.findByEmail(email);
		User userEntity = userRepository.findByUsername(email);
		if (userEntity == null) {
			return returnValue;
		}
		String token = jwtProvider.generatePasswordRequestToken(userEntity.getUsername());
		PasswordResetTokenEntity passwordResetTokenEntity = new PasswordResetTokenEntity();
		passwordResetTokenEntity.setToken(token);
		passwordResetTokenEntity.setUserDetails(userEntity);

		passwordResetTokenRepository.save(passwordResetTokenEntity);

		returnValue = emailUtility.sendPasswordResetRequest(userEntity.getUsername(), userEntity.getUsername(), token);

		return returnValue;
	}

	@Override
	public boolean resetPassword(String token, String password) {
		boolean returnValue = false;

		if (jwtProvider.hasTokenExpired(token)) {
			return returnValue;
		}

		PasswordResetTokenEntity passwordResetTokenEntity = passwordResetTokenRepository.findByToken(token);
		if (passwordResetTokenEntity == null) {
			return returnValue;
		}
		// prepare encoded password
		String encodedPasword = encoder.encode(password);
		/// Update user password in DB
		User user = passwordResetTokenEntity.getUserDetails();
		user.setPassword(encodedPasword);

		User savedUser = userRepository.save(user);

		// verify if password saved successfully

		if (savedUser != null && savedUser.getPassword().equalsIgnoreCase(encodedPasword)) {
			returnValue = true;
		}

		// Remove PasswordResetTokenEntity from DB
		passwordResetTokenRepository.delete(passwordResetTokenEntity);

		return returnValue;
	}

}
